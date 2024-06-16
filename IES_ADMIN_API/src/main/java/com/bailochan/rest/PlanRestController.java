package com.bailochan.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bailochan.bindings.PlanForm;
import com.bailochan.service.PlanService;

@RestController
@RequestMapping("/admin")
public class PlanRestController {

    @Autowired
    private PlanService planService;

    @PostMapping("/CreatePlans")
    public ResponseEntity<String> createPlan(@RequestBody PlanForm planForm) {
        boolean created = planService.createPlan(planForm);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Plan created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create plan");
        }
    }

    @GetMapping("/fetchPlans")
    public ResponseEntity<List<PlanForm>> fetchPlans() {
        List<PlanForm> plans = planService.fetchPlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/plans/{planId}")
    public ResponseEntity<?> getPlanById(@PathVariable Integer planId) {
        PlanForm plan = planService.getPlanById(planId);
        if (plan != null) {
            return ResponseEntity.ok(plan);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plan with ID " + planId + " not found");
        }
    }


    @PutMapping("/plans/{planId}/{status}")
    public ResponseEntity<String> changePlanStatus(@PathVariable Integer planId, @PathVariable String status) {
        String result = planService.changePlanStatus(planId, status);
        if (result.equals("Plan status changed successfully")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("Plan not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan with ID " + planId + " not found");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change plan status");
        }
    }





    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<String> deletePlan(@PathVariable Integer planId) {
        String result = planService.deletePlan(planId);
        if (result.equals("successfully deleted")) { // Updated condition
            return ResponseEntity.ok(result);
        } else if (result.equals("does not exist")) { // Updated condition
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan with ID " + planId + " not found");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete plan");
        }
    }
}
