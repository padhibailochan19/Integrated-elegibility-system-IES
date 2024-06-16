package com.bailochan.service;

import java.util.List;

import com.bailochan.binding.CitizenApp;

public interface AppRegService {

	public String createCitizenApp(CitizenApp app);
	
	public List<CitizenApp> getAllApplications(Integer userId, String userType);
	
}
