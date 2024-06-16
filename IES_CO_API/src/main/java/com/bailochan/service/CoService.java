package com.bailochan.service;

import java.util.List;

import com.bailochan.bindings.CoInfo;
import com.bailochan.entities.CoNoticeEntity;

public interface CoService {
	
 
	List<CoNoticeEntity> getNoticesByStatus(String status);
	
	public boolean printNotice(Integer noticeId);
}
