package com.bailochan.repo;
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




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bailochan.enity.CitizenAppEntity;

public interface CitizenAppRepo extends JpaRepository<CitizenAppEntity, Integer> {

    // Custom query to find a CitizenAppEntity by citizenSsn
    @Query("SELECT ca FROM CitizenAppEntity ca WHERE ca.citizenSsn = :ssn")
    public List<CitizenAppEntity> findByCitizenSsn(@Param("ssn") Long ssn);

	public List<CitizenAppEntity> findByCreatedBy(Integer userId);
    
}
