package com.bailochan.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bailochan.entity.EligEntity;

import com.bailochan.repo.EligRepo;

import com.bailochan.request.SearchRequest;
import com.bailochan.response.SearchResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EligRepo eligRepo;

	@Override
	public List<String> getUniquePlanNames() {
		return eligRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		return eligRepo.findPlanStatuses();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {

		List<SearchResponse> response = new ArrayList<>();

		EligEntity queryBuilder = new EligEntity();
				
		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}

//		LocalDate planStartDate = request.getEligStartDate();
//		if (planStartDate != null) {
//			queryBuilder.setEligStartDate(planStartDate);
//		}
//
//		LocalDate planEndDate = request.getEligEndDate();
//		if (planEndDate != null) {
//			queryBuilder.setEligEndDate(planEndDate);
//		}

		Example<EligEntity> example = Example.of(queryBuilder);

		List<EligEntity> entities = eligRepo.findAll(example);

//		for (EligEntity entity : entities) {
//			SearchResponse sr = new SearchResponse();
//			BeanUtils.copyProperties(entity, sr);
//			response.add(sr);
//		}
		
		for (EligEntity entity : entities) {
            SearchResponse sr = new SearchResponse();
            // Set properties in SearchResponse from EligEntity
            sr.setCitizenEmail(entity.getCitizenEmail());
            sr.setPlanName(entity.getPlanName());
            sr.setCitizenName(entity.getCitizenName());
            sr.setCitizenGender(entity.getCitizenGender());
            sr.setCitizenPhno(entity.getCitizenPhno());
            sr.setCitizenSsn(entity.getCitizenSsn());

            response.add(sr);
        }

		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
	    List<EligEntity> entities = eligRepo.findAll();

	    HSSFWorkbook workBook = new HSSFWorkbook();

	    HSSFSheet sheet = workBook.createSheet();
	    HSSFRow headerRow = sheet.createRow(0);

	    headerRow.createCell(0).setCellValue("Name");
	    headerRow.createCell(1).setCellValue("Email");
	    headerRow.createCell(2).setCellValue("Plan Start Date");
	    headerRow.createCell(3).setCellValue("Plan End Date");
	    headerRow.createCell(4).setCellValue("Benefit Amt");

	    int i = 1;

	    for (EligEntity entity : entities) {
	        HSSFRow dataRow = sheet.createRow(i);
	        dataRow.createCell(0).setCellValue(entity.getPlanName());
	        dataRow.createCell(1).setCellValue(entity.getCitizenEmail());
	        dataRow.createCell(2).setCellValue(String.valueOf(entity.getEligStartDate()));
	        dataRow.createCell(3).setCellValue(String.valueOf(entity.getEligEndDate()));

	        // Add logging to track the value of benefitAmt
	        Double benefitAmt = entity.getBenefitAmt();
//	        System.out.println("Benefit Amount for entity " + entity.getId() + ": " + benefitAmt);
	        
	        // Check if the benefit amount is null before setting it in the Excel sheet
	        if (benefitAmt != null) {
	            dataRow.createCell(4).setCellValue(benefitAmt);
	        } else {
	            dataRow.createCell(4).setCellValue(""); // or set any default value you want
	        }

	        i++;
	    }

	    ServletOutputStream outputStream = response.getOutputStream();
	    workBook.write(outputStream);
	    workBook.close();
	    outputStream.close();
	}


	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {
		List<EligEntity> entities = eligRepo.findAll();

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Search Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Start Date ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan End Date ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Benefit Amt", font));
		table.addCell(cell);

		for (EligEntity entity : entities) {
			table.addCell(entity.getPlanName());
			table.addCell(entity.getCitizenEmail());
			table.addCell(String.valueOf(entity.getEligStartDate()));
			table.addCell(String.valueOf(entity.getEligEndDate()));
			table.addCell(String.valueOf(entity.getBenefitAmt()));
		}
		
		document.add(table);
		
		document.close();
	}
}
