package com.bailochan.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CO_NOTICES")
public class CoNoticeEntity {

	@Id
	@GeneratedValue
	private Integer noticeId;
	private String noticeStatus;
	private LocalDate noticePrintDate;
	private String noticeS3Url;
	private Long caseNum;
	private Integer edgTraceId;
	
	private String planName;
	
	private String citizenEmail;  // by me
	
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public LocalDate getNoticePrintDate() {
		return noticePrintDate;
	}
	public void setNoticePrintDate(LocalDate noticePrintDate) {
		this.noticePrintDate = noticePrintDate;
	}
	public String getNoticeS3Url() {
		return noticeS3Url;
	}
	public void setNoticeS3Url(String noticeS3Url) {
		this.noticeS3Url = noticeS3Url;
	}
	public Long getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(Long caseNum) {
		this.caseNum = caseNum;
	}
	public Integer getEdgTraceId() {
		return edgTraceId;
	}
	public void setEdgTraceId(Integer edgTraceId) {
		this.edgTraceId = edgTraceId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getCitizenEmail() {
		return citizenEmail;
	}
	public void setCitizenEmail(String citizenEmail) {
		this.citizenEmail = citizenEmail;
	}
	
//	@OneToOne
//	@JoinColumn(name = "case_num")
//	private AppEntity app;

	

	
}
