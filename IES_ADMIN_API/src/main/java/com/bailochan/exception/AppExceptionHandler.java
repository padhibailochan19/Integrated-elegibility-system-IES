package com.bailochan.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<AppExcepiton> handleException(Exception ex) {
        String exMsg = ex.getMessage(); // Extract the exception message if needed
        logger.error("Exception Occurred : ", ex);

        AppExcepiton appException = new AppExcepiton();
        appException.setExCode("EX0003");
        appException.setExDesc("Request method 'POST' is not supported"); // Fixed description
        appException.setExDate(LocalDateTime.now());

        return new ResponseEntity<>(appException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
