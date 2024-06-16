package com.bailochan.repositories;

//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import in.ashokit.entities.AppEntity;
//
//public interface AppRepo extends JpaRepository<AppEntity, Integer> {
//	
//	@Query(value = "from AppEntity where userId =:userId")
//	public List<AppEntity> fetchUserApps();
//
//	@Query(value = "from AppEntity where userId =:userId")
//	public List<AppEntity> fetchCwApps(Integer userId);
//
//}





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bailochan.entities.AppEntity;

public interface AppRepo extends JpaRepository<AppEntity, Long> {
	
	@Query("SELECT a FROM AppEntity a WHERE a.user.userId = :userId")
	public List<AppEntity> fetchUserApps(@Param("userId") Integer userId);
	
	@Query("SELECT a FROM AppEntity a WHERE a.user.userId = :userId")
	public List<AppEntity> fetchCwApps(@Param("userId") Integer userId);

}


