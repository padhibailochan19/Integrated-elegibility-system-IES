package com.bailochan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bailochan.entities.EligEntity;

public interface EligRepo extends JpaRepository<EligEntity, Integer> {
	
//	@Query("from EligEntity where caseNum = :caseNum")
//	public EligEntity fetchByCaseNum(Long caseNum);
	
	
	@Query("from EligEntity where caseNum=:caseNum")
	public EligEntity findByCaseNum(Long caseNum);
}
