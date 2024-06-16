package com.bailochan.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailochan.bindings.PlanForm;
import com.bailochan.entities.PlanEntity;
import com.bailochan.repositories.PlanRepo;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepo planRepo;

    @Override
    public boolean createPlan(PlanForm planForm) {
        PlanEntity planEntity = new PlanEntity();
        BeanUtils.copyProperties(planForm, planEntity);
        planRepo.save(planEntity);
        return true;
    }

    @Override
    public List<PlanForm> fetchPlans() {
    	List<PlanEntity> planEntities = planRepo.findAll();
    	List<PlanForm> planForms = new ArrayList<>();

    	for (PlanEntity planEntity : planEntities) {
    		PlanForm planForm = new PlanForm();
    	    BeanUtils.copyProperties(planEntity, planForm);
    	    planForms.add(planForm);
    	}

    	return planForms;

    }

    @Override
    public PlanForm getPlanById(Integer planId) {
        Optional<PlanEntity> optional = planRepo.findById(planId);
        if (optional.isPresent()) {
            PlanEntity planEntity = optional.get();
            PlanForm planForm = new PlanForm();
            BeanUtils.copyProperties(planEntity, planForm);
            return planForm;
        }
        return null;
    }

    @Override
    public String changePlanStatus(Integer planId, String status) {
        Optional<PlanEntity> optional = planRepo.findById(planId);
        if (optional.isPresent()) {
            PlanEntity planEntity = optional.get();
            planEntity.setStatus(status);
            planRepo.save(planEntity);
            return "Plan status changed successfully";
        }
        return "Plan not found";
    }

    @Override
    public String deletePlan(Integer planId) {
        Optional<PlanEntity> optional = planRepo.findById(planId);
        if (optional.isPresent()) {
            planRepo.deleteById(planId);
            return "successfully deleted";
        } else {
            return "does not exist";
        }
    }

}
