package com.bailochan.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailochan.bindings.DashboardCard;
import com.bailochan.bindings.LoginForm;
import com.bailochan.bindings.UserAccForm;
import com.bailochan.constants.AppConstants;
import com.bailochan.entities.EligEntity;
import com.bailochan.entities.UserEntity;
import com.bailochan.repositories.EligRepo;
import com.bailochan.repositories.PlanRepo;
import com.bailochan.repositories.UserRepo;
import com.bailochan.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PlanRepo planRepo;

    @Autowired
    private EligRepo eligRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public String login(LoginForm loginForm) {

        UserEntity entity = userRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());

        if(entity == null){
            return AppConstants.INVALID_CRED;
        }

        if(AppConstants.Y_STR.equals(entity.getActiveSw()) && AppConstants.UNLOCKED.equals(entity.getAccStatus())){
            return AppConstants.SUCCESS;
        }else {
            return AppConstants.ACC_LOCKED;
        }
    }

    @Override
    public boolean recoverPwd(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        if(null == userEntity){
            return false;
        }else{
        	String subject = AppConstants.RECOVER_SUB;
    		String body = readEmailBody(AppConstants.PWD_BODY_FILE, userEntity);
          return emailUtils.sendEmail(subject, body, email);
        }
    }

    @Override
    public DashboardCard fetchDashboardInfo() {
        long plansCount = planRepo.count();
        

        List<EligEntity> eligList = eligRepo.findAll();

        Long approvedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.AP)).count();

        Long deniedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.DN)).count();

      
        
        double totalBenefitAmt = eligList.stream()
                .mapToDouble(ed -> {
                    Double benefitAmt = ed.getBenefitAmt();
                    return benefitAmt != null ? benefitAmt : 0.0;
                })
                .sum();

        DashboardCard card = new DashboardCard();

        card.setPlansCnt(plansCount);
        card.setApprovedCnt(approvedCnt);
        card.setDeniedCnt(deniedCnt);
        card.setBeniftAmtGiven(totalBenefitAmt);
        
        
//        String userEmails = eligList.stream()
//                .map(EligEntity::getCitizenEmail)
//                .collect(Collectors.joining(", "));
//
//		//Check if userEmails is empty or null
//		if (userEmails != null && !userEmails.isEmpty()) {
//		card.setUser(userEmails);
//		} else {
//		card.setUser("No users found"); // Or any default value you prefer
//		}
//
//        DashboardCard dashboardCard = new DashboardCard();
//        
//        dashboardCard.setUser(userEmails);
        

        return card;
    }

    
    @Override
    public UserAccForm getUserByEmail(String email) {
    	UserEntity userEntity = userRepo.findByEmail(email);
    	UserAccForm user = new UserAccForm();
    	BeanUtils.copyProperties(userEntity, user);
    	return user;
    }
    
    private String readEmailBody(String filename, UserEntity user) {
		StringBuilder sb = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace(AppConstants.FNAME, user.getFullName());
				line = line.replace(AppConstants.PWD, user.getPwd());
				line = line.replace(AppConstants.EMAIL, user.getEmail());
				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}


	
}
