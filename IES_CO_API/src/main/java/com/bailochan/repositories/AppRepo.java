package com.bailochan.repositories;

//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import in.ashokit.entities.AppEntity;
//
//public interface AppRepo extends JpaRepository<AppEntity, Long> {
//
//	public List<AppEntity> fetchUserApps();
//
//	@Query(value = "from AppEntity where userId =:userId")
//	public List<AppEntity> fetchCwApps(Integer userId);
//
//}


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Import this for @Param annotation

import com.bailochan.entities.AppEntity;
import com.bailochan.entities.UserEntity;

public interface AppRepo extends JpaRepository<AppEntity, Long> {

    // Assuming you want to fetch all apps for a user
    @Query("SELECT app FROM AppEntity app WHERE app.user = :user")
    public List<AppEntity> fetchUserApps(@Param("user") UserEntity user);

    // Assuming you want to fetch apps based on userId
    @Query("SELECT app FROM AppEntity app WHERE app.user.id = :userId")
    public List<AppEntity> fetchCwApps(@Param("userId") Integer userId);
}


