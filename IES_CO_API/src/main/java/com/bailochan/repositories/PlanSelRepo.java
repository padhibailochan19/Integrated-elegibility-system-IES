package com.bailochan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bailochan.entities.PlanSelEntity;

public interface PlanSelRepo extends JpaRepository<PlanSelEntity, Integer>{
	
	@Query("from PlanSelEntity where app.caseNum=:caseNum")
	public PlanSelEntity findByCaseNum(Long caseNum);

}
