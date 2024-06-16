package com.bailochan.service;

import java.util.Map;

import com.bailochan.bindings.DcSummary;
import com.bailochan.bindings.Education;
import com.bailochan.bindings.Income;
import com.bailochan.bindings.Kids;
import com.bailochan.bindings.PlanSelection;

public interface DcService {

	public Map<Integer, String> getPlanNames();
	
	public boolean savePlanSelection(PlanSelection planSel);

	public boolean saveIncome(Income income);

	public boolean saveEducation(Education education);

	public boolean saveKids(Kids kids);

	public DcSummary fetchSummaryInfo(Long caseNum);

}
