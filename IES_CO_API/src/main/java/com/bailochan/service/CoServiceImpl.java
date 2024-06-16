package com.bailochan.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bailochan.bindings.CoInfo;
import com.bailochan.bindings.EligInfo;
import com.bailochan.entities.AppEntity;
import com.bailochan.entities.CitizenAppEntity;
import com.bailochan.entities.CoNoticeEntity;
import com.bailochan.entities.EligEntity;
import com.bailochan.repositories.AppRepo;
import com.bailochan.repositories.CitizenAppRepo;
import com.bailochan.repositories.CoNoticeRepo;
import com.bailochan.repositories.EligRepo;
import com.bailochan.utils.EmailUtils;
import com.bailochan.utils.PdfUtils;
import com.bailochan.utils.S3Utils;

import jakarta.persistence.Cache;

@Service
public class CoServiceImpl implements CoService {
	
	@Autowired
	private CoNoticeRepo coNoticeRepo;

	@Autowired
	private EligRepo eligRepo;
	
	@Autowired
	private PdfUtils pdfUtils;
	
	@Autowired 
	private S3Utils s3Utils;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private AppRepo appRepo;
	
	@Autowired
	CitizenAppRepo citizenAppRepo;

	@Override
	public boolean printNotice(Integer noticeId) {
		
		CoNoticeEntity coEntity = coNoticeRepo.findById(noticeId).get();
		Long caseNum = coEntity.getCaseNum();
		AppEntity appEntity = appRepo.findById(caseNum).get(); 
		
		// get Eligibility data
		AppEntity app = new AppEntity();
		app.setCaseNum(caseNum);
		EligEntity eligDtlsEntity = eligRepo.findByCaseNum(caseNum);
		EligInfo eligInfo = new EligInfo();
		BeanUtils.copyProperties(eligDtlsEntity, eligInfo);
		
		File generatePdf = pdfUtils.generatePdf(eligInfo);
		String objUrl = s3Utils.uploadObject(generatePdf);
		
		coEntity.setNoticeS3Url(objUrl);
		coEntity.setNoticePrintDate(LocalDate.now());
		coEntity.setNoticeStatus("History");
		coNoticeRepo.save(coEntity);
		
		
		// get email from AppEntity 
    	Optional<CitizenAppEntity> appEntityOptional = citizenAppRepo.findById(caseNum);
    	if (appEntityOptional.isPresent()) {
    		CitizenAppEntity citizenAppEntity = appEntityOptional.get();
    	    String email = citizenAppEntity.getCitizenEmail();
    	    // Use the email as needed
    	    System.out.println("Email retrieved from AppEntity: " + email);
    	    
    	    String to = email;
    	    String subject = "Application Case Number Created";
    	    String body = "<html><body>";
    	    body += "<p>Dear User,</p>";
    	    body += "<p>Your application case number has been successfully created.</p>";
    	    body += "<p>Case Number: " + citizenAppEntity.getCaseNum() + "</p>";
    	    body += "<p>Thank you for using our services.</p>";
    	    body += "<p>Best regards,<br>IES team</p>";
    	    body += "</body></html>";


    		emailUtils.sendEmail(to, subject, body, generatePdf);
    	} else {
    	    // Handle the scenario where no AppEntity is found for the given caseNum
    	    System.out.println("No AppEntity found for caseNum: " + caseNum);
    	}
		
		
		
		// generate notice
		
		// upload to s3
		
		// update notice url in db
		
		// send notice in email as attachment
		
		return true;
	}

	@Override
    public List<CoNoticeEntity> getNoticesByStatus(String status) {
        if ("History".equalsIgnoreCase(status)) {
            return coNoticeRepo.findByNoticeStatus("History");
        } else if ("P".equalsIgnoreCase(status)) {
            return coNoticeRepo.findByNoticeStatus("P");
        } else {
            // Return an empty list or handle invalid status input as needed
            return Collections.emptyList();
        }
    }

}
