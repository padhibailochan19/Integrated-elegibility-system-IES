package com.bailochan.service;

import com.bailochan.request.LoginRequest;
import com.bailochan.request.PwdChangeRequest;
import com.bailochan.request.SignUpRequest;
import com.bailochan.response.LoginResponse;
import com.bailochan.response.SignUpResponse;

public interface UserService {

	public SignUpResponse saveUser(SignUpRequest request);
	
	public LoginResponse userLogin(LoginRequest request);
	
	public LoginResponse updatePwd(PwdChangeRequest request);
	
	public boolean recoverPwd(String email);
}
