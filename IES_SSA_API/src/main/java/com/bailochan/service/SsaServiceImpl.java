package com.bailochan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SsaServiceImpl implements SsaService {

    private static final Logger logger = LoggerFactory.getLogger(SsaServiceImpl.class);

    @Value("${ssa.api.url}")
    private String SSA_API_URL;

    @Override
    public String getStateName(String ssn) {
        // Check if SSN starts with specific digits and return state name accordingly
        if (ssn.startsWith("6")) {
            return "New Jersey";
        } else if (ssn.startsWith("5")) {
            return "Rhode Island";
        } else if (ssn.startsWith("4")) {
            return "Ohio";
        } else if (ssn.startsWith("3")) {
            return "California";
        }
        
        // If SSN doesn't match any of the predefined patterns, try fetching state name from the API
        String fullUrl = SSA_API_URL + "/" + ssn;

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Make a GET request to the API URL
            return restTemplate.getForObject(fullUrl, String.class);
        } catch (HttpClientErrorException ex) {
            // Log the exception
            logger.error("Error occurred while fetching state name for SSN {} from API: {}", ssn, ex.getMessage());
            // Handle different HTTP error statuses
            if (ex.getStatusCode().is4xxClientError()) {
                // Handle 4xx client errors (e.g., 404 Not Found)
                return "SSN not found";
            } else if (ex.getStatusCode().is5xxServerError()) {
                // Handle 5xx server errors
                return "Internal server error";
            } else {
                // Handle other exceptions
                return "Error occurred while fetching state name from API";
            }
        } catch (Exception ex) {
            // Log any other unexpected exceptions
            logger.error("Unexpected error occurred while fetching state name for SSN {} from API: {}", ssn, ex.getMessage());
            return "Error occurred while fetching state name from API";
        }
    }
}
