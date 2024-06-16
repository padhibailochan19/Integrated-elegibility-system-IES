package com.bailochan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.bindings.EdResponse;
import com.bailochan.service.EligService;


@RestController
@RequestMapping("/ed")
public class EdRestController {

	@Autowired
	private EligService eligService;

	@GetMapping("/elig-determine/{caseNum}")
	public ResponseEntity<EdResponse> determineElig(@PathVariable Long caseNum) {
		EdResponse response = eligService.determineEligibility(caseNum);
		return new ResponseEntity<EdResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/co/{caseNum}")
	public ResponseEntity<String> generateCo(@PathVariable Long caseNum) {
	    try {
	        boolean status = eligService.generateCo(caseNum);
	        if (status) {
	            return new ResponseEntity<>("Notice Generated", HttpStatus.OK);
	        } else {
	            // Return a 404 Not Found status for an invalid case number
	            return new ResponseEntity<>("Notice Not Generated: Invalid Case Number", HttpStatus.NOT_FOUND);
	        }
	    } catch (IllegalArgumentException e) {
	        // Handle the case where an invalid case number is provided
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	        // Handle other exceptions
	        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


}
