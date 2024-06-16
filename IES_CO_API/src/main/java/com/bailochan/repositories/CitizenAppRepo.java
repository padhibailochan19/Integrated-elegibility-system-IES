package com.bailochan.repositories;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import in.ashokit.enity.CitizenAppEntity;
//
//public interface CitizenAppRepo extends JpaRepository<CitizenAppEntity, Integer> {
//
//	
//	public CitizenAppEntity findByCitizenSsn(Long ssn);
//	
//	public List<CitizenAppEntity> findByCreatedBy(Integer userId);
//}

import org.springframework.data.jpa.repository.JpaRepository;

import com.bailochan.entities.CitizenAppEntity;

public interface CitizenAppRepo extends JpaRepository<CitizenAppEntity, Long>{
	
}
