package com.bailochan.service;

import com.bailochan.bindings.EdResponse;


public interface EligService {

	public EdResponse determineEligibility(Long caseNum);

	public boolean generateCo(Long caseNum);
}
