package com.bailochan.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bailochan.entities.CoNoticeEntity;

public interface CoNoticeRepo extends JpaRepository<CoNoticeEntity, Integer>{

	@Query("from CoNoticeEntity where caseNum = :caseNum")
	public List<CoNoticeEntity> fetchByCaseNum(Long caseNum);
	
	@Query("SELECT c FROM CoNoticeEntity c WHERE c.noticeStatus = :status")
	public List<CoNoticeEntity> findByNoticeStatus(String status);
	
}
