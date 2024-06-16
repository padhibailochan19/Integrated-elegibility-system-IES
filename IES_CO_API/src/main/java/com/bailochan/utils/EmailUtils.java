package com.bailochan.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

//
//@Component
//public class EmailUtils {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public boolean sendEmail(String to, String subject, String body, File f) {
//        boolean isSent = false;
//        try {
//            // Trim whitespace from the email address
//            to = to.trim();
//            
//            // Validate email address format
//            if (!isValidEmailAddress(to)) {
//                System.err.println("Invalid email address: " + to);
//                return false;
//            }
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(body, true);
//            mailSender.send(mimeMessage);
//            isSent = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isSent;
//    }
//
//    // Method to validate email address format
//    private boolean isValidEmailAddress(String email) {
//        // Regular expression to validate email address format
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        return email.matches(emailRegex);
//    }
//}



@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body, File attachment) {
        boolean isSent = false;
        try {
            // Null check and trim whitespace from the email address
            if (to != null) {
                to = to.trim();
            } else {
                System.err.println("Email address is null");
                return false;
            }

            // Validate email address format
            if (!isValidEmailAddress(to)) {
                System.err.println("Invalid email address: " + to);
                return false;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true for multipart message
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true for HTML content

            if (attachment != null) {
                helper.addAttachment(attachment.getName(), attachment); // Attach the PDF file
            }

            mailSender.send(mimeMessage);
            isSent = true;
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
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





