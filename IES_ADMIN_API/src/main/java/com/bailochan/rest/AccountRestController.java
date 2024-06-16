package com.bailochan.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.bindings.UnlockAccForm;
import com.bailochan.bindings.UserAccForm;
import com.bailochan.service.AccountService;
import com.bailochan.service.SignUpResponse;

@RestController
@RequestMapping("/admin")
public class AccountRestController {
	
	private Logger logger = LoggerFactory.getLogger(AccountRestController.class);

    @Autowired
    private AccountService accService;

    @PostMapping("/user")
    public ResponseEntity<String> createAccount(@RequestBody UserAccForm userAccForm) {
        logger.debug("Account Creation Process Started...");
        
        try {
            SignUpResponse status = accService.createUserAccount(userAccForm);
            logger.debug("Account Creation Process Completed...");
            
            if (status != null && status.getSuccessMsg() != null) {
                logger.info("Account Created Successfully.");
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(status.getSuccessMsg());
            } else {
                logger.error("Account Creation Failed: " + status.getErrorMsg());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(status.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("Exception occurred during account creation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Exception occurred during account creation");
        }
    }

    @GetMapping("/viewAllUsers")
    public ResponseEntity<List<UserAccForm>> getUsers(){
    	logger.debug("Fetching User Accounts process started..");
        List<UserAccForm> userAccForms = accService.fetchUserAccounts();
        logger.debug("Fetching User Accounts process completed..");
        logger.info("User Accounts Fetched success....");
        return new ResponseEntity<>(userAccForms, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Integer userId){
        UserAccForm userAccById = accService.getUserAccById(userId);
        if (userAccById != null) {
            logger.info("User account fetched successfully.");
            return new ResponseEntity<>(userAccById, HttpStatus.OK);
        } else {
            String errorMessage = "User with ID " + userId + " not found.";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }


    
    @PutMapping("/user/{userId}")
    public ResponseEntity<String> unlockUserAccount(@PathVariable("userId") Integer userId,
                                                    @RequestBody UnlockAccForm unlockAccForm) {
        logger.debug("User account unlock process started..");
        
        // Call the unlockUserAccount method from the service
        String unlockResult = accService.unlockUserAccount(unlockAccForm);
        
        logger.debug("User account unlock process completed..");
        logger.info("User account status updated successfully..");
        
        return new ResponseEntity<>(unlockResult, HttpStatus.OK);
    }
    
    // added late
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        String deleteMessage = accService.deleteUserAccount(userId);
        return deleteMessage;
    }


}
