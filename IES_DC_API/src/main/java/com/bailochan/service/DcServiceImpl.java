package com.bailochan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bailochan.bindings.DcSummary;
import com.bailochan.bindings.Education;
import com.bailochan.bindings.Income;
import com.bailochan.bindings.Kid;
import com.bailochan.bindings.Kids;
import com.bailochan.bindings.PlanSelection;
import com.bailochan.entities.AppEntity;
import com.bailochan.entities.EducationEntity;
import com.bailochan.entities.IncomeEntity;
import com.bailochan.entities.KidEntity;
import com.bailochan.entities.PlanEntity;
import com.bailochan.entities.PlanSelEntity;
import com.bailochan.entities.UserEntity;
import com.bailochan.repositories.AppRepo;
import com.bailochan.repositories.EducationRepo;
import com.bailochan.repositories.IncomeRepo;
import com.bailochan.repositories.KidRepo;
import com.bailochan.repositories.PlanRepo;
import com.bailochan.repositories.PlanSelRepo;
import com.bailochan.repositories.UserRepo;

@Service
public class DcServiceImpl implements DcService {
	
	@Autowired
	private PlanRepo planRepo;
	
	@Autowired
	private AppRepo appRepo;
	
	@Autowired
	private IncomeRepo incomeRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EducationRepo eduRepo;
	
	@Autowired
	private KidRepo kidRepo;
	
	@Autowired
	private PlanSelRepo planSelRepo;

	@Override
	public Map<Integer, String> getPlanNames() {
		List<PlanEntity> plans = planRepo.findAll();
		
		Map<Integer, String> plansMap = new HashMap<>();
		
		for(PlanEntity entity : plans) {
			plansMap.put(entity.getPlanId(), entity.getPlanName());
		}
		
		return plansMap;
	}

//	@Override
//	public boolean savePlanSelection(PlanSelection planSel) {
//	    PlanSelEntity entity = new PlanSelEntity();
//	    BeanUtils.copyProperties(planSel, entity);
//	    Long caseNum = planSel.getCaseNum();
//	    Integer userId = planSel.getUserId();
//	    Integer planId = planSel.getPlanId();
//	    
//	    // Check if the caseNum exists
//	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
//	    if (appEntityOptional.isEmpty()) {
//	        // Case number does not exist
//	        return false;
//	    }
//	    
//	    // Check if the userId exists
//	    Optional<UserEntity> userEntityOptional = userRepo.findById(userId);
//	    if (userEntityOptional.isEmpty()) {
//	        // User ID does not exist
//	        return false;
//	    }
//	    
//	    // Check if the planId exists
//	    Optional<PlanEntity> planEntityOptional = planRepo.findById(planId);
//	    if (planEntityOptional.isEmpty()) {
//	        // Plan ID does not exist
//	        return false;
//	    }
//	    
//	    // Case number, user ID, and planId exist, proceed with saving the PlanSelection entity
//	    AppEntity appEntity = appEntityOptional.get();
//	    UserEntity userEntity = userEntityOptional.get();
//	    PlanEntity planEntity = planEntityOptional.get();
//	    
//	    // Set the AppEntity, UserEntity, and PlanEntity for the PlanSelection
//	    entity.setApp(appEntity);
//	    entity.setUser(userEntity);
//	    entity.setPlan(planEntity);
//	    
//	    
//	    // Save the PlanSelectionEntity
//	    planSelRepo.save(entity);  
//	    
//	    return true;
//	}
	
	
	
//	@Override
//	public boolean savePlanSelection(PlanSelection planSel) {
//	    PlanSelEntity entity = new PlanSelEntity();
//	    BeanUtils.copyProperties(planSel, entity);
//	    Long caseNum = planSel.getCaseNum();
//	    Integer userId = planSel.getUserId();
//	    Integer planId = planSel.getPlanId();
//	    
//	    // Check if the caseNum exists
//	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
//	    if (appEntityOptional.isEmpty()) {
//	        // Case number does not exist
//	        return false;
//	    }
//	    
//	    // Check if the userId exists
//	    Optional<UserEntity> userEntityOptional = userRepo.findById(userId);
//	    if (userEntityOptional.isEmpty()) {
//	        // User ID does not exist
//	        return false;
//	    }
//	    
//	    // Check if the planId exists
//	    Optional<PlanEntity> planEntityOptional = planRepo.findById(planId);
//	    if (planEntityOptional.isEmpty()) {
//	        // Plan ID does not exist
//	        return false;
//	    }
//	    
//	    // Case number, user ID, and planId exist, proceed with saving the PlanSelection entity
//	    AppEntity appEntity = appEntityOptional.get();
//	    UserEntity userEntity = userEntityOptional.get();
//	    PlanEntity planEntity = planEntityOptional.get();
//	    
//	    // Set the AppEntity, UserEntity, and PlanEntity for the PlanSelection
//	    entity.setApp(appEntity);
//	    entity.setUser(userEntity);
//	    entity.setPlan(planEntity);
//	    
//	    // Set the PlanId, UserId, and CaseNum for the AppEntity
//	    appEntity.setPlanId(planId);
////	    appEntity.setUserId(userId);
//	    appEntity.setCaseNum(caseNum);
//	    
//	    // Save the PlanSelectionEntity and return the saved entity
//	    PlanSelEntity savedEntity = planSelRepo.save(entity);  
//	    
//	    return savedEntity != null;
//	}
	
	@Override
	public boolean savePlanSelection(PlanSelection planSel) {
	    PlanSelEntity entity = new PlanSelEntity();
	    BeanUtils.copyProperties(planSel, entity);
	    Long caseNum = planSel.getCaseNum();
	    Integer userId = planSel.getUserId();
	    Integer planId = planSel.getPlanId();
	    
	    // Check if the caseNum exists
	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
	    if (appEntityOptional.isEmpty()) {
	        // Case number does not exist
	        return false;
	    }
	    
	    // Check if the userId exists
	    Optional<UserEntity> userEntityOptional = userRepo.findById(userId);
	    if (userEntityOptional.isEmpty()) {
	        // User ID does not exist
	        return false;
	    }
	    
	    // Check if the planId exists
	    Optional<PlanEntity> planEntityOptional = planRepo.findById(planId);
	    if (planEntityOptional.isEmpty()) {
	        // Plan ID does not exist
	        return false;
	    }
	    
	    // Case number, user ID, and planId exist, proceed with saving the PlanSelection entity
	    AppEntity appEntity = appEntityOptional.get();
	    UserEntity userEntity = userEntityOptional.get();
	    PlanEntity planEntity = planEntityOptional.get();
	    
	    // Set the AppEntity, UserEntity, and PlanEntity for the PlanSelection
	    entity.setApp(appEntity);
	    entity.setUser(userEntity);
	    entity.setPlan(planEntity);
	    
	    // Set the PlanId, UserId, and CaseNum for the AppEntity
	    appEntity.setPlanId(planId);
//	    appEntity.setUserId(userId); // Assuming you want to set the user ID
	    appEntity.setCaseNum(caseNum);
	    
	    // Save the PlanSelectionEntity and return the saved entity
	    PlanSelEntity savedEntity = planSelRepo.save(entity);  
	    
	    return savedEntity != null;
	}





	@Override
	public boolean saveIncome(Income income) {
	    // Create an instance of IncomeEntity
	    IncomeEntity entity = new IncomeEntity();
	    
	    // Copy properties from Income to IncomeEntity
	    BeanUtils.copyProperties(income, entity);
	    
	    // Extract caseNum and userId from the Income object
	    Long caseNum = income.getCaseNum();
	    Integer userId = income.getUserId();
	    
	    // Check if the caseNum exists
	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
	    if (appEntityOptional.isEmpty()) {
	        // Case number does not exist
	        return false;
	    }
	    
	    // Check if the userId exists
	    Optional<UserEntity> userEntityOptional = userRepo.findById(userId);
	    if (userEntityOptional.isEmpty()) {
	        // User ID does not exist
	        return false;
	    }
	    
	    // Case number and user ID exist, proceed with saving the income entity
	    AppEntity appEntity = appEntityOptional.get();
	    UserEntity userEntity = userEntityOptional.get();
	    
	    // Set the AppEntity and UserEntity for the IncomeEntity
	    entity.setApp(appEntity);
	    entity.setUser(userEntity);
	    
	    // Save the IncomeEntity
	    incomeRepo.save(entity);
	    
	    return true;
	}


	@Override
	public boolean saveEducation(Education edu) {
	    Long caseNum = edu.getCaseNum();
	    Integer userId = edu.getUserId();
	    
	    // Check if the caseNum exists
	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
	    if (appEntityOptional.isEmpty()) {
	        // Case number does not exist
	        //throw new IllegalArgumentException("Invalid case number: " + caseNum);
	        // Alternatively, you can return false here instead of throwing an exception
	        // return false;
	    	return false;
	    }
	    
	    // Check if the userId exists
	    Optional<UserEntity> userEntityOptional = userRepo.findById(userId);
	    if (userEntityOptional.isEmpty()) {
	        // User ID does not exist
	        //throw new IllegalArgumentException("Invalid user ID: " + userId);
	        // Alternatively, you can return false here instead of throwing an exception
	        // return false;
	    	
	    	return false;
	    }
	    
	    // Case number and user ID exist, proceed with saving the education entity
	    AppEntity appEntity = appEntityOptional.get();
	    UserEntity userEntity = userEntityOptional.get();
	    
	    EducationEntity entity = new EducationEntity();
	    BeanUtils.copyProperties(edu, entity);
	    
	    entity.setUser(userEntity);
	    entity.setApp(appEntity);
	    
	    eduRepo.save(entity);
	    
	    return true;
	}

	@Override
	public boolean saveKids(Kids kids) {
	    Long caseNum = kids.getCaseNum();
	    Integer userId = kids.getUserId();

	    // Check if the caseNum exists
	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
	    if (appEntityOptional.isEmpty()) {
	        // Case number does not exist
	        //throw new IllegalArgumentException("Invalid case number: " + caseNum);
	    	
	    	return false;
	    }

	    // Check if the userId exists
	    Optional<UserEntity> userEntityOptional = userRepo.findById(userId);
	    if (userEntityOptional.isEmpty()) {
	        // User ID does not exist
	        //throw new IllegalArgumentException("Invalid user ID: " + userId);
	    	
	    	return false;
	    			
	    }

	    // Both caseNum and userId are valid, proceed with saving the kids
	    AppEntity appEntity = appEntityOptional.get();
	    UserEntity userEntity = userEntityOptional.get();

	    List<Kid> kidsList = kids.getKidsList();

	    try {
	        for (Kid kid : kidsList) {
	            KidEntity entity = new KidEntity();
	            BeanUtils.copyProperties(kid, entity);

	            entity.setApp(appEntity);
	            entity.setUser(userEntity);

	            kidRepo.save(entity);
	        }
	    } catch (DataIntegrityViolationException e) {
	        // Handle the case of duplicate entry violation
	        // You can log the error if needed
	        return false;
	    }


	    return true;
	}


	@Override
	public DcSummary fetchSummaryInfo(Long caseNum) {
	    DcSummary summary = new DcSummary();
	    
	    if (caseNum == null) {
	        throw new IllegalArgumentException("The given caseNum must not be null");
	    }
	    
	    // Find AppEntity by caseNum
	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
	    if (appEntityOptional.isEmpty()) {
	        // Handle the case where no AppEntity is found for the given caseNum
	        // For example, you can throw an exception, return null, or return an empty summary
	        // Here, we'll throw an exception for demonstration purposes
	        throw new NoSuchElementException("No AppEntity found for caseNum: " + caseNum);
	    }

	    AppEntity appEntity = appEntityOptional.get();

	    // Find PlanEntity by planId
	    PlanEntity planEntity = planRepo.findById(appEntity.getPlanId()).orElse(null);

	    // Find IncomeEntity by caseNum
	    IncomeEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);

	    // Find EducationEntity by caseNum
	    EducationEntity educationEntity = eduRepo.findByCaseNum(caseNum);

	    // Find KidEntities by caseNum
	    List<KidEntity> kidEntities = kidRepo.findByCaseNum(caseNum);
	    
	    // Populate summary object
	    summary.setCaseNum(caseNum);
	    summary.setPlanName(planEntity != null ? planEntity.getPlanName() : null);

	    Income income = new Income();
	    if (incomeEntity != null) {
	        BeanUtils.copyProperties(incomeEntity, income);
	    }
	    summary.setIncome(income);

	    Education edu = new Education();
	    if (educationEntity != null) {
	        BeanUtils.copyProperties(educationEntity, edu);
	    }
	    summary.setEducation(edu);

	    List<Kid> kidsList = new ArrayList<>();
	    for (KidEntity entity : kidEntities) {
	        Kid kid = new Kid();
	        BeanUtils.copyProperties(entity, kid);
	        kidsList.add(kid);
	    }
	    Kids kids = new Kids();
	    kids.setKidsList(kidsList);
	    summary.setKids(kids);

	    return summary;
	}
	
	
//	@Override
//	public DcSummary fetchSummaryInfo(Long caseNum) {
//	    DcSummary summary = new DcSummary();
//	    
//	    if (caseNum == null) {
//	        throw new IllegalArgumentException("The given caseNum must not be null");
//	    }
//	    
//	    // Find AppEntity by caseNum
//	    Optional<AppEntity> appEntityOptional = appRepo.findById(caseNum);
//	    if (appEntityOptional.isEmpty()) {
//	        throw new NoSuchElementException("No AppEntity found for caseNum: " + caseNum);
//	    }
//
//	    AppEntity appEntity = appEntityOptional.get();
//
//	    // Find PlanEntity by planId
//	    PlanEntity planEntity = planRepo.findById(appEntity.getPlanId()).orElse(null);
//
//	    // Find IncomeEntity by caseNum
//	    IncomeEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);
//
//	    // Find EducationEntity by caseNum
//	    EducationEntity educationEntity = eduRepo.findByCaseNum(caseNum);
//
//	    // Find KidEntities by caseNum
//	    List<KidEntity> kidEntities = kidRepo.findByCaseNum(caseNum);
//	    
//	    // Populate summary object
//	    summary.setCaseNum(caseNum);
//	    summary.setPlanName(planEntity != null ? planEntity.getPlanName() : null);
//
////	    summary.setUserId(appEntity.getUserId()); // Set userId from AppEntity
//
//	    Income income = new Income();
//	    if (incomeEntity != null) {
//	        BeanUtils.copyProperties(incomeEntity, income);
//	    }
//	    summary.setIncome(income);
//
//	    Education edu = new Education();
//	    if (educationEntity != null) {
//	        BeanUtils.copyProperties(educationEntity, edu);
//	    }
//	    summary.setEducation(edu);
//
//	    List<Kid> kidsList = new ArrayList<>();
//	    for (KidEntity entity : kidEntities) {
//	        Kid kid = new Kid();
//	        BeanUtils.copyProperties(entity, kid);
//	        kidsList.add(kid);
//	    }
//	    Kids kids = new Kids();
//	    kids.setKidsList(kidsList);
//	    summary.setKids(kids);
//
//	    return summary;
//	}




}
