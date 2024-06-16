package com.bailochan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bailochan.entities.PlanEntity;

public interface PlanRepo extends JpaRepository<PlanEntity, Integer> {
}
