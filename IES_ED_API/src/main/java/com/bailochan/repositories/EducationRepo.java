package com.bailochan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bailochan.entities.EducationEntity;

public interface EducationRepo extends JpaRepository<EducationEntity, Integer>{
	
	@Query("from EducationEntity where app.caseNum=:caseNum")
	public EducationEntity findByCaseNum(Long caseNum);

}
