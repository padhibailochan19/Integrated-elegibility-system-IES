package com.bailochan.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailochan.bindings.UnlockAccForm;
import com.bailochan.bindings.UserAccForm;
import com.bailochan.entities.UserEntity;
import com.bailochan.repositories.UserRepo;
import com.bailochan.utils.EmailUtils;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public SignUpResponse createUserAccount(UserAccForm accForm) {
		
		SignUpResponse response = new SignUpResponse();
		
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(accForm, entity);

		// Generate a unique temporary password
	    String tempPwd = generatePwd();
		entity.setPwd(tempPwd);
		
		// set acc status
		entity.setAccStatus("LOCKED");
		userRepo.save(entity);
		

//	// send email
//		String subject = "User Registration";
//		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
//		return emailUtils.sendEmail(subject, body, accForm.getEmail());

		
		// send email to user with credentials
		String subject = "IES - Account Created ";
		String body = "<html><body>";
		body += "<h2>IES - Account Created </h2>";
		body += "<p>Your temporary password to login into IES is: <strong>" + tempPwd + "</strong></p>";
		body += "<p>Thank you for registering with us!</p>";
		body += "<p>Best regards,<br>IES Team</p>";
		body += "</body></html>";

		boolean isSent = emailUtils.sendEmail(accForm.getEmail(), subject, body);

		if (isSent) {
		    response.setSuccessMsg("Registration Success");
		} else {
		    response.setErrorMsg("Registration Failed");
		}
		return response;
	}
	
	
	

	@Override
	public List<UserAccForm> fetchUserAccounts() {

		List<UserEntity> userEntities = userRepo.findAll();

		List<UserAccForm> users = new ArrayList<UserAccForm>();

		for (UserEntity userEntity : userEntities) {
			UserAccForm user = new UserAccForm();
			BeanUtils.copyProperties(userEntity, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public UserAccForm getUserAccById(Integer accId) {
		Optional<UserEntity> optional = userRepo.findById(accId);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			UserAccForm user = new UserAccForm();
			BeanUtils.copyProperties(userEntity, user);
			return user;
		}
		return null;
	}

	@Override
	public String changeAccStatus(Integer userId, String status) {
		int cnt = userRepo.updateAccStatus(userId, status);

		if (cnt > 0) {
			return "Status Changed";
		}

		return "Failed To Change";
	}

	@Override
	public String unlockUserAccount(UnlockAccForm unlockAccForm) {

		UserEntity entity = userRepo.findByEmail(unlockAccForm.getEmail());

		entity.setPwd(unlockAccForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");

		userRepo.save(entity);

		return "Account Unlocked";
	}

	private String generatePwd() {
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		// combine all strings
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 6;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphaNumeric.length());

			// get character specified by index
			// from the string
			char randomChar = alphaNumeric.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		return sb.toString();
	}

	private String readEmailBody(String filename, UserEntity user) {
		StringBuilder sb = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace("${FNAME}", user.getFullName());
				line = line.replace("${TEMP_PWD}", user.getPwd());
				line = line.replace("${EMAIL}", user.getEmail());
				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}



	// Added late

	@Override
	public String deleteUserAccount(Integer userId) {
	    Optional<UserEntity> userOptional = userRepo.findById(userId);
	    if (userOptional.isPresent()) {
	        userRepo.deleteById(userId);
	        return "User Account with ID " + userId + " Plan deleted successfully";
	    } else {
	        return "User Account with ID " + userId + " does not exist";
	    }
	}






	

}
