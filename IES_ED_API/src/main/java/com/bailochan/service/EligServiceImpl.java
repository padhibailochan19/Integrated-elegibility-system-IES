package com.bailochan.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailochan.bindings.EdResponse;
import com.bailochan.entities.AppEntity;
import com.bailochan.entities.CitizenAppEntity;
import com.bailochan.entities.CoNoticeEntity;
import com.bailochan.entities.EducationEntity;
import com.bailochan.entities.EligEntity;
import com.bailochan.entities.IncomeEntity;
import com.bailochan.entities.KidEntity;
import com.bailochan.entities.PlanEntity;
import com.bailochan.entities.PlanSelEntity;
import com.bailochan.repositories.AppRepo;
import com.bailochan.repositories.CitizenAppRepo;
import com.bailochan.repositories.CoNoticeRepo;
import com.bailochan.repositories.EducationRepo;
import com.bailochan.repositories.EligRepo;
import com.bailochan.repositories.IncomeRepo;
import com.bailochan.repositories.KidRepo;
import com.bailochan.repositories.PlanRepo;
import com.bailochan.repositories.PlanSelRepo;

@Service
public class EligServiceImpl implements EligService {

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private PlanRepo planRepo;

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private KidRepo kidRepo;

    @Autowired
    private EducationRepo eduRepo;

    @Autowired
    private CoNoticeRepo coNoticeRepo;

    @Autowired
    private EligRepo eligRepo;
    
    @Autowired
    private PlanSelRepo planSelRepo;
    
    @Autowired
    CitizenAppRepo citizenAppRepo;

    @Override
    public EdResponse determineEligibility(Long caseNum) {
        EdResponse response = new EdResponse();

//        if (caseNum == null) {
//            response.setPlanStatus("Denied");
//            response.setDenialRsn("Case Number must not be null");
//            return response;
//        }
//
//        if (!appRepo.existsById(caseNum)) {
//            response.setPlanStatus("Denied");
//            response.setDenialRsn("Invalid Case Number");
//            return response;
//        }
        
    	// get citizen plan name using caseNum
        
        PlanSelEntity planSel = planSelRepo.findByCaseNum(caseNum);
        Integer planId = planSel.getPlanId();
        PlanEntity planEntity = planRepo.findById(planId).get();
    	String planName = planEntity.getPlanName();
    	
    	// get email from AppEntity 
    	Optional<CitizenAppEntity> appEntityOptional = citizenAppRepo.findById(caseNum);
    	if (appEntityOptional.isPresent()) {
    		CitizenAppEntity appEntity = appEntityOptional.get();
    	    String email = appEntity.getCitizenEmail();
    	    //get name,gender,phno,ssn
    	   String citizenName = appEntity.getCitizenName();
    	    String citizenGender = appEntity.getCitizenGender();
    	     String citizenPhno = appEntity.getCitizenPhno();
    	    Long citizenSsn = appEntity.getCitizenSsn();
    	    
    	    // Use the email as needed
    	    response.setCitizenEmail(email);  // setting Email into EdResponse
    	    response.setCitizenName(citizenName);
    	    response.setCitizenGender(citizenGender);
    	    response.setCitizenPhno(citizenPhno);
    	    response.setCitizenSsn(citizenSsn);
    	    
    	    System.out.println("Email retrieved from AppEntity: " + email);
    	} else {
    	    // Handle the scenario where no AppEntity is found for the given caseNum
    	    System.out.println("No AppEntity found for caseNum: " + caseNum);
    	}
    	
    	
        
//        AppEntity appEntity = appRepo.findById(caseNum).get();
//        Integer planId = appEntity.getPlanId();
//        PlanEntity planEntity = planRepo.findById(planId).get();
//        String planName = planEntity.getPlanName();

    	
    	// get citizen info using caseNum
    	IncomeEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);
		EducationEntity educationEntity = eduRepo.findByCaseNum(caseNum);
		List<KidEntity> kidsEntities = kidRepo.findByCaseNum(caseNum);
		response.setPlanName(planName);
		response.setCaseNum(caseNum);
		

		if ("SNAP".equals(planName)) {

			Double salaryIncome = incomeEntity.getSalaryIncome();
			if (salaryIncome > 30000) {
				response.setPlanStatus("DN");
				response.setDenialRsn("High Salary Income");
			} else {
				response.setPlanStatus("AP");
				response.setBenefitAmt(10500.00);
				response.setPlanStartDate(LocalDate.now());
				response.setPlanEndDate(LocalDate.now().plusMonths(6));
			}
		} else if ("CCAP".equals(planName)) {
			Double salaryIncome = incomeEntity.getSalaryIncome();
            List<KidEntity> kids = kidRepo.findByCaseNum(caseNum);
            boolean isValid = true;
            for (KidEntity kid : kids) {
                LocalDate kidDob = kid.getKidDob();
                int years = Period.between(kidDob, LocalDate.now()).getYears();
                if (years > 16) {
                    isValid = false;
                    break;
                }
            }

            if (isValid && salaryIncome <= 30000 && !kids.isEmpty()) {
            	response.setPlanStatus("AP");
            } else {
            	response.setPlanStatus("DN");
            }

            if (!isValid) {
            	response.setDenialRsn("Kid age above 16");
            }
            if (salaryIncome > 30000) {
            	response.setDenialRsn("High Income");
            }
            if (kids.isEmpty()) {
            	response.setDenialRsn("No Kids Available");
            }
		} else if ("Medicaid".equals(planName)) {
        	Double salaryIncome = incomeEntity.getSalaryIncome();
            Double propertyIncome = incomeEntity.getPropertyIncome();
            Double rentIncome = incomeEntity.getRentIncome();

            if (salaryIncome <= 30000 && (propertyIncome + rentIncome) <= 0) {
            	response.setPlanStatus("Ap");
            } else {
            	response.setPlanStatus("DN");
            	response.setDenialRsn("High Income");
            }
		} else if ("Medicare".equals(planName)) {
        	AppEntity entity = new AppEntity();   // by me.................
            LocalDate dob = entity.getDob();
            int years = Period.between(dob, LocalDate.now()).getYears();

            if (years >= 65) {
            	response.setPlanStatus("AP");
            } else {
            	response.setPlanStatus("DN");
            	response.setDenialRsn("Age Not Matched");
            }
		} else if ("RIW".equals(planName)) {
            EducationEntity edu = eduRepo.findByCaseNum(caseNum);
            Integer graduationYear = edu.getGraduationYear();
            Double salaryIncome = incomeEntity.getSalaryIncome();

            if (graduationYear != null && salaryIncome != null) {
                response.setPlanStatus("AP");
            } else {
                response.setPlanStatus("DN");
            }
            if (graduationYear == null) {
                response.setDenialRsn("Under Graduate");
            }
            if (salaryIncome != null) {
                response.setDenialRsn("Already Employee");
            }
        }

        if (response.getPlanStatus().equals("AP")) {
            response.setPlanStartDate(LocalDate.now());
            response.setPlanEndDate(LocalDate.now().plusMonths(6));
            response.setBenefitAmt(20000.00);
        }

//        generateCorrespondence(appEntity);

        EligEntity entity = new EligEntity();
        BeanUtils.copyProperties(response, entity);
        entity.setEligStartDate(response.getPlanStartDate()); // Copy planStartDate
        entity.setEligEndDate(response.getPlanEndDate());
        entity.setCitizenEmail(response.getCitizenEmail());
        entity.setCitizenName(response.getCitizenName());
        entity.setCitizenPhno(response.getCitizenPhno());
        entity.setCitizenGender(response.getCitizenGender());
        entity.setCitizenSsn(response.getCitizenSsn());

        eligRepo.save(entity);

        return response;
    }


//    private void generateCorrespondence(AppEntity app) {
//        CoEntity entity = new CoEntity();
//        entity.setNoticeStatus("Pending");
//        entity.setApp(app);
//
//        try {
//            coRepo.save(entity);
//        } catch (DataIntegrityViolationException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean generateCo(Long caseNum) {
        try {
            // Retrieve the EligEntity record based on the caseNum
            EligEntity byCaseNum = eligRepo.findByCaseNum(caseNum);
            
            
            // Create a new instance of CoNoticeEntity
            CoNoticeEntity coEntity = new CoNoticeEntity();
            
            // Populate the fields of CoNoticeEntity
            coEntity.setNoticeStatus("P");
            coEntity.setCaseNum(caseNum);
            
         // Retrieve the planName from the EligEntity
            String planName = byCaseNum.getPlanName();
            coEntity.setPlanName(planName);
            
            
         // Retrieve the citizenEmail from the EligEntity
            String citizenEmail = byCaseNum.getCitizenEmail();

            // Set the citizenEmail in the CoNoticeEntity
            coEntity.setCitizenEmail(citizenEmail);

            
           
            coEntity.setEdgTraceId(byCaseNum.getEdgTraceId()); // Assuming this relationship is meaningful
            
            // Save the CoNoticeEntity
            coNoticeRepo.save(coEntity);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    
}
