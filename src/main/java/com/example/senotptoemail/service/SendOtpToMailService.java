package com.example.senotptoemail.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class SendOtpToMailService {

    private final JavaMailSender javaMailSender;

    // Inject JavaMailSender thông qua constructor
    @Autowired
    public SendOtpToMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtpService(String email) {
        String otp = generateOtp();
        try {
            sendOtpToMail(email, otp);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending otp to email", e);
        }
    }
    
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOtpToMail(String email, String otp) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("OTP for email verification");
        mimeMessageHelper.setText("Your OTP is: " + otp);
        javaMailSender.send(mimeMessage);
    }
}

