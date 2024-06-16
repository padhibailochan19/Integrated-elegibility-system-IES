package com.bailochan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bailochan.entities.IncomeEntity;

public interface IncomeRepo extends JpaRepository<IncomeEntity, Integer> {

//	@Query("from IncomeEntity where caseNum=:caseNum")
//	public IncomeEntity findByCaseNum(Integer caseNum);
	
	@Query("select ie from IncomeEntity ie join ie.app ae where ae.caseNum = :caseNum")
	public IncomeEntity findByCaseNum(Long caseNum);


}
