package com.bailochan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.bailochan.entity.EligEntity;
import java.util.List;



public interface EligRepo extends JpaRepository<EligEntity, Integer> {
	
//	@Query("from EligEntity where caseNum=:caseNum")
//	public EligEntity findByCaseNum(Long caseNum);
//	
//	@Query("SELECT e FROM EligEntity e JOIN CitizenAppEntity c ON e.caseNum = c.caseNum WHERE c.citizenEmail = :citizenEmail")
//	public EligEntity findByCitizenEmail(String citizenEmail);
//	
//	@Query("from EligEntity where citizenEmail=:email")
//	public EligEntity findByCitizenEmail(String email);
	
	
	@Query("select distinct(planName) from EligEntity")
	public List<String> findPlanNames();

	@Query("select distinct(planStatus) from EligEntity")
	public List<String> findPlanStatuses();
	
}



