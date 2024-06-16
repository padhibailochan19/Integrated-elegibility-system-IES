package com.bailochan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.request.LoginRequest;
import com.bailochan.request.PwdChangeRequest;
import com.bailochan.request.SignUpRequest;
import com.bailochan.response.LoginResponse;
import com.bailochan.response.SignUpResponse;
import com.bailochan.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserService service;
	

	
	@PostMapping("/register")
//	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<SignUpResponse> saveUSer(@RequestBody SignUpRequest request){
		
		SignUpResponse response = service.saveUser(request);
		
		if(response.getSuccessMsg()!=null) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}else if(response.getErrorMsg().contains("Duplicate Email")) {
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
//	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest request){
		
		LoginResponse response = service.userLogin(request);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/pwd-change")
	public ResponseEntity<LoginResponse> updatePwd(@RequestBody PwdChangeRequest request){
		LoginResponse login = service.updatePwd(request);
		return new ResponseEntity<>(login,HttpStatus.OK);
	}
	
	@GetMapping("/recover-pwd/{email}")
	public ResponseEntity<String> recoverPwd(@PathVariable String email) {
		boolean isValid = service.recoverPwd(email);
		
		if(isValid) {
			return new ResponseEntity<>("Password sent to your email",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Invalid Email",HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
