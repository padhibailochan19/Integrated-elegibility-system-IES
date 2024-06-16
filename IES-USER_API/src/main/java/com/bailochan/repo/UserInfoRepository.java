package com.bailochan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bailochan.entity.UserInfoEntity;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
	
	public UserInfoEntity findByEmail(String email);
}
