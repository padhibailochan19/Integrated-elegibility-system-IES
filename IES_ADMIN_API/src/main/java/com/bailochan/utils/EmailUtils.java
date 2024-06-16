package com.bailochan.utils;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

//@Component
//public class EmailUtils {
//
//	@Autowired
//	private JavaMailSender mailSender;
//
//	public boolean sendEmail(String to, String subject, String body) {
//		boolean isSent = false;
//		try {
//			MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//			helper.setTo(to);
//			helper.setSubject(subject);
//			helper.setText(body, true);
//			mailSender.send(mimeMessage);
//			isSent = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return isSent;
//	}
//}




@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body) {
        boolean isSent = false;
        try {
            // Trim whitespace from the email address
            to = to.trim();
            
            // Validate email address format
            if (!isValidEmailAddress(to)) {
                System.err.println("Invalid email address: " + to);
                return false;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSent;
    }

    // Method to validate email address format
    private boolean isValidEmailAddress(String email) {
        // Regular expression to validate email address format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}