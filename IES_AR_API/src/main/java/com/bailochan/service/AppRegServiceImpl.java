
package com.bailochan.service;


import org.springframework.web.client.RestTemplate;

import com.bailochan.binding.CitizenApp;
import com.bailochan.enity.CitizenAppEntity;
import com.bailochan.repo.CitizenAppRepo;

import java.util.ArrayList; // Add import statement
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AppRegServiceImpl implements AppRegService {
    
    @Value("${ssa.api.url}")
    private String SSA_API_URL;
    
    @Autowired
    private CitizenAppRepo appRepo;

    @Override
    public String createCitizenApp(CitizenApp app) {
        Long citizenSsn = app.getCitizenSsn();
        
        List<CitizenAppEntity> appEntities = appRepo.findByCitizenSsn(citizenSsn);
        
        if (!appEntities.isEmpty()) {
            return "Duplicate Application";
        }
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> forEntity = rt.getForEntity(SSA_API_URL, String.class, citizenSsn);
        String body = forEntity.getBody();
        
        if (body.equals("Rhode Island")) {
            CitizenAppEntity entity = new CitizenAppEntity();
            BeanUtils.copyProperties(app, entity);
            CitizenAppEntity save = appRepo.save(entity);
            return "Application Created with case Number - " + save.getCaseNum();
        }
        return "Invalid SSN";
    }


    @Override
    public List<CitizenApp> getAllApplications(Integer userId, String userType) {
        List<CitizenAppEntity> entities = null;
        List<CitizenApp> apps = new ArrayList<>();
        
        // Retrieve citizen applications based on user ID and user type
        if ("Admin".equals(userType)) {
            // If the user is an admin, retrieve all applications
            entities = appRepo.findAll();
        } else {
            // If the user is not an admin, retrieve applications created by the specified user ID
            entities = appRepo.findByCreatedBy(userId);
        }
       
        // Convert entities to DTOs and add them to the list
        for (CitizenAppEntity entity : entities) {
            CitizenApp app = new CitizenApp();
            
            BeanUtils.copyProperties(entity, app);
            
            apps.add(app);
            
        }
        
        return apps;
    }
}
