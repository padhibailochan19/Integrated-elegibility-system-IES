package com.bailochan.bindings;

import java.util.List;

public class Kids {

	private List<Kid> kidsList;

	private Long caseNum;

	private Integer userId;

	public List<Kid> getKidsList() {
		return kidsList;
	}

	public void setKidsList(List<Kid> kidsList) {
		this.kidsList = kidsList;
	}

	public Long getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(Long caseNum) {
		this.caseNum = caseNum;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
