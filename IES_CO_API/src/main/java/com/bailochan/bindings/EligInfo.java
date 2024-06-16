package com.bailochan.bindings;

import java.time.LocalDate;

public class EligInfo {

	private Long caseNum;

	private String planName;

	private String planStatus;

//	private LocalDate planStartDate;
//
//	private LocalDate planEndDate;
	
	private LocalDate eligStartDate;

	private LocalDate eligEndDate;

	

	public LocalDate getEligStartDate() {
		return eligStartDate;
	}

	public void setEligStartDate(LocalDate eligStartDate) {
		this.eligStartDate = eligStartDate;
	}

	public LocalDate getEligEndDate() {
		return eligEndDate;
	}

	public void setEligEndDate(LocalDate eligEndDate) {
		this.eligEndDate = eligEndDate;
	}

	private Double benefitAmt;

	private String denialRsn;

	public Long getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(Long caseNum) {
		this.caseNum = caseNum;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}


//	public LocalDate getPlanStartDate() {
//		return planStartDate;
//	}
//
//	public void setPlanStartDate(LocalDate planStartDate) {
//		this.planStartDate = planStartDate;
//	}
//
//	public LocalDate getPlanEndDate() {
//		return planEndDate;
//	}
//
//	public void setPlanEndDate(LocalDate planEndDate) {
//		this.planEndDate = planEndDate;
//	}

	public Double getBenefitAmt() {
		return benefitAmt;
	}

	public void setBenefitAmt(Double benefitAmt) {
		this.benefitAmt = benefitAmt;
	}

	public String getDenialRsn() {
		return denialRsn;
	}

	public void setDenialRsn(String denialRsn) {
		this.denialRsn = denialRsn;
	}

}
