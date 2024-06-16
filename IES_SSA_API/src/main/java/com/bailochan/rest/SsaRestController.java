package com.bailochan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.service.SsaService;

@RestController
@RequestMapping("/api")
public class SsaRestController {

    @Autowired
    private SsaService ssaService;

    @Value("${ssa.api.url}")
    private String ssaApiUrl;

    @GetMapping("/ssn/{ssn}")
    public String getStateNames(@PathVariable String ssn) {
        // Assuming the service implementation fetches data from the configured API URL
        return ssaService.getStateName(ssn);
    }
}
