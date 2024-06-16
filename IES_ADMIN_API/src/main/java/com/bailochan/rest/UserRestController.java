package com.bailochan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.bindings.DashboardCard;
import com.bailochan.bindings.LoginForm;
import com.bailochan.bindings.UserAccForm;
import com.bailochan.entities.EligEntity;
import com.bailochan.service.UserService;

@RestController
@RequestMapping("/admin")
public class UserRestController {

	@Autowired
	private UserService userService;
	

	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		String status = userService.login(loginForm);
		if (status.equals("success")) {
			return "redirect:/dashboard?email=" + loginForm.getEmail();
		} else {
			return status;
		}
	}
	
	

	@GetMapping("/dashboard")
	public ResponseEntity<DashboardCard> buildDashboard(@RequestParam("email") String email) {
		EligEntity eligEntity = new EligEntity();
		String citizenEmail = eligEntity.getCitizenEmail();
		
//		UserAccForm user = userService.getUserByEmail(email);
		
		DashboardCard dashboardCard = userService.fetchDashboardInfo();
		dashboardCard.setUser(citizenEmail);
		return new ResponseEntity<>(dashboardCard, HttpStatus.OK);
	}
}
