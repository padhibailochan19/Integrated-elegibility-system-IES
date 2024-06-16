package com.bailochan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bailochan.entities.EligEntity;
import org.springframework.data.jpa.repository.Query;


public interface EligRepo extends JpaRepository<EligEntity, Integer> {
	
//	@Query("from EligEntity where caseNum=:caseNum")
//	public EligEntity findByCaseNum(Long caseNum);
//	
////	@Query("SELECT e FROM EligEntity e JOIN CitizenAppEntity c ON e.caseNum = c.caseNum WHERE c.citizenEmail = :citizenEmail")
////	public EligEntity findByCitizenEmail(String citizenEmail);
//	
//	@Query("from EligEntity where citizenEmail=:email")
//	public EligEntity findByCitizenEmail(String email);
	
	
}

