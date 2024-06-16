package com.bailochan.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bailochan.entities.KidEntity;

public interface KidRepo extends JpaRepository<KidEntity, Integer> {

	 @Query("from KidEntity k where k.app.caseNum = :caseNum")
	 public List<KidEntity> findByCaseNum(Long caseNum);


}
