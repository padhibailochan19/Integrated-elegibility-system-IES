package com.bailochan.response;


import lombok.Data;

@Data
public class SearchResponse {

	private String planName;
	private String citizenName;
	private String citizenEmail;
	
	private String citizenPhno;  
	private String citizenGender;   
	private Long citizenSsn;  
	

}
