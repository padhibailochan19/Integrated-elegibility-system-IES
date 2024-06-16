package com.bailochan.service;

import java.util.List;

import com.bailochan.bindings.PlanForm;

public interface PlanService {

    public boolean createPlan(PlanForm planForm);

    public List<PlanForm> fetchPlans( );

    public PlanForm getPlanById(Integer planId);

    public String changePlanStatus(Integer planId, String status);
    
    String deletePlan(Integer planId); // late added

}
