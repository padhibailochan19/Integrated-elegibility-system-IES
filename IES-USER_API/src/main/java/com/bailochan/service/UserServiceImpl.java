package com.bailochan.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bailochan.entity.UserInfoEntity;
import com.bailochan.repo.UserInfoRepository;
import com.bailochan.request.LoginRequest;
import com.bailochan.request.PwdChangeRequest;
import com.bailochan.request.SignUpRequest;
import com.bailochan.response.DasboardResponse;
import com.bailochan.response.LoginResponse;
import com.bailochan.response.SignUpResponse;
import com.bailochan.utils.EmailUtils;

import io.micrometer.core.ipc.http.HttpSender.Response;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserInfoRepository userRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public SignUpResponse saveUser(SignUpRequest request) {
		
		// for duplicate mail -> Account already exist with this mail
		// for registration success -> Registration success
		// any exception -> Registration Failed
		
		SignUpResponse response = new SignUpResponse();
		
		
		// unique email validation
		
		UserInfoEntity user = userRepo.findByEmail(request.getEmail());
		if(user!=null) {
			response.setErrorMsg("Duplicate Email");
			return response;
		}
		
		// generate temporary pwd
		String tempPwd = generateTempPwd();
		request.setPwd(tempPwd);
		request.setPwdChanged("false");
		
		// save user record in db
		UserInfoEntity entity = new UserInfoEntity();
		BeanUtils.copyProperties(request, entity);
		userRepo.save(entity);
		
		// send email to user with credentials
		String subject = "IES - Account Created";
		String body = "Your Pwd To Login into IES :: " + tempPwd;
		
		boolean isSent =  emailUtils.sendEmail(request.getEmail(), subject, body);
		
		if(isSent) {
			response.setSuccessMsg("Registration Success");
		}else {
			response.setErrorMsg("Registration Failed");
		}
		return response;
	}

	@Override
	public LoginResponse userLogin(LoginRequest request) {
		
		LoginResponse response = new LoginResponse();
		//  1 : findByMethod    2 : customQuery  3: QBE (QueryByExample)
		
		//check login credential
		
		UserInfoEntity entity = new UserInfoEntity();
		entity.setEmail(request.getEmail());
		entity.setPwd(request.getPwd());
		
		Example<UserInfoEntity> exmp = Example.of(entity);
		List<UserInfoEntity> entities = userRepo.findAll(exmp);
		
		if(!entities.isEmpty()) {
			UserInfoEntity user = entities.get(0);
			
			response.setUserid(user.getUserId());
			response.setUserType(user.getUserType());
			
			if(user.getPwdChanged().equals("true")) {
				// second login
				response.setPwdChanged("true");
				response.setValidLonin(true);
				response.setUserid(user.getUserId());
				response.setUserType(user.getUserType());
				
				// set dashboard data
				DasboardResponse dashboard = new DasboardResponse();
				dashboard.setPlansCount(6l);
				dashboard.setBenefitAmtTotal(3400.00);
				dashboard.setCitizensApcnt(1000l);
				dashboard.setCitizensDncnt(500l);
				
				response.setDashboard(dashboard);
				
			}
			else {
				// first login
				response.setPwdChanged("true");
				response.setValidLonin(true);
			}
		}
		else {
			response.setValidLonin(false);
		}
		
		return response;
	}

	@Override
	public LoginResponse updatePwd(PwdChangeRequest request) {
		
		LoginResponse response = new LoginResponse();
		
		Integer userId = request.getUserId();
		
		Optional<UserInfoEntity> findById = userRepo.findById(userId);
		
		
		if(findById.isPresent()) {
			
			//update pwd
			UserInfoEntity entity = findById.get();
			entity.setPwd(request.getPwd());
			entity.setPwdChanged("true");
			userRepo.save(entity);
		
			
			// construct  dashboard response
			
			response.setUserid(entity.getUserId());
			response.setUserType(entity.getUserType());
			response.setValidLonin(true);
			response.setPwdChanged("true");
			
			DasboardResponse dashboard = new DasboardResponse();
			dashboard.setPlansCount(6l);
			dashboard.setBenefitAmtTotal(3400.00);
			dashboard.setCitizensApcnt(1000l);
			dashboard.setCitizensDncnt(500l);
			
			response.setDashboard(dashboard);
			
		}
		return response;
	}
	

	@Override
	public boolean recoverPwd(String email) {
		UserInfoEntity user = userRepo.findByEmail(email);
		
		if(user == null) {
			return false;
		}

		String subject = "IES - Recover Password";
		String body = "Your Password :: " + user.getPwd();
		return emailUtils.sendEmail(email, subject, body);
	}
	
	public String generateTempPwd() {
		// create a string of all characters
	    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

	    // create random string builder
	    StringBuilder sb = new StringBuilder();

	    // create an object of Random class
	    Random random = new Random();

	    // specify length of random string
	    int length = 5;

	    for(int i = 0; i < length; i++) {

	      // generate random index number
	      int index = random.nextInt(alphabet.length());

	      // get character specified by index
	      // from the string
	      char randomChar = alphabet.charAt(index);

	      // append the character to string builder
	      sb.append(randomChar);
	    }

	   return sb.toString();

	}
	
}
