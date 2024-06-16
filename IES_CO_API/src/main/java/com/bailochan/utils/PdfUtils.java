package com.bailochan.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import com.bailochan.bindings.EligInfo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PdfUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    public File generatePdf(EligInfo edInfo) {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a new content stream for adding text
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define font and font size
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Define text formatting
            int margin = 50;
            int yPosition = (int) (page.getMediaBox().getHeight() - margin);
            int lineHeight = 15;

            // Write Eligibility Information to PDF
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Case Number: " + edInfo.getCaseNum());
            contentStream.newLineAtOffset(0, -lineHeight);
            contentStream.showText("Plan Name: " + (edInfo.getPlanName() != null ? edInfo.getPlanName() : ""));
            contentStream.newLineAtOffset(0, -lineHeight);
            contentStream.showText("Plan Status: " + edInfo.getPlanStatus());
            contentStream.newLineAtOffset(0, -lineHeight);
            contentStream.showText("Plan Start Date: " + (edInfo.getEligStartDate() != null ? edInfo.getEligStartDate().format(DateTimeFormatter.ISO_DATE) : ""));
            contentStream.newLineAtOffset(0, -lineHeight);
            contentStream.showText("Plan End Date: " + (edInfo.getEligEndDate() != null ? edInfo.getEligEndDate().format(DateTimeFormatter.ISO_DATE) : ""));
            contentStream.newLineAtOffset(0, -lineHeight);
            contentStream.showText("Benefit Amount: " + edInfo.getBenefitAmt());
            contentStream.newLineAtOffset(0, -lineHeight);
            contentStream.showText("Denial Reason: " + edInfo.getDenialRsn());
            contentStream.endText();

            // Close the content stream
            contentStream.close();

            // Save the document to a file
            File pdfFile = File.createTempFile("generated", ".pdf");
            document.save(pdfFile);
            document.close();

            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }
}
