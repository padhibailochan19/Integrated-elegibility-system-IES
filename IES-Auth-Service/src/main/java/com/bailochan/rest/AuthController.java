package com.bailochan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.binding.AuthRequest;
import com.bailochan.binding.AuthResponse;
import com.bailochan.entity.UserCredential;
import com.bailochan.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public String addNewUser(@RequestBody UserCredential user) {
		return service.saveUser(user);
	}

	@PostMapping("/token")
	public AuthResponse getToken(@RequestBody AuthRequest authRequest) {

		AuthResponse response = new AuthResponse();

		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			String token = null;

			if (authenticate.isAuthenticated()) {
				token = service.generateToken(authRequest.getUsername());
				response.setToken(token);
				response.setLoginValid("yes");
			}
		} catch (Exception e) {
			response.setToken("");
			response.setLoginValid("no");
		}

		return response;
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return "Token is valid";
	}
}