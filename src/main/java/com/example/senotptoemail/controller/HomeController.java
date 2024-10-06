package com.example.senotptoemail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.senotptoemail.service.SendOtpToMailService; 

@RestController
public class HomeController {
    @Autowired
    private SendOtpToMailService sendOtpToMailService;
    @GetMapping("/")
    public String home() {
        return "Welcome to send otp to mail spring boot!";
    }
    @PostMapping("sendOtp/{email}")
    public String sendOtpToEmail(@PathVariable("email") String email) {
        sendOtpToMailService.sendOtpService(email); 
                
        return "send otp to email successfully";          
    }
    

}
