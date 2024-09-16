package com.example.SanChoi247.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SanChoi247.model.entity.San;
import com.example.SanChoi247.model.entity.User;
import com.example.SanChoi247.model.repo.LoaiSanRepo;
import com.example.SanChoi247.model.repo.LoginRepo;
import com.example.SanChoi247.model.repo.SanRepo;

import jakarta.servlet.http.HttpSession;
@Controller
public class LoginController {
    @Autowired
    SanRepo sanRepo;
    @Autowired
    LoaiSanRepo loaiSanRepo;
    @Autowired
    LoginRepo loginRepo;
    @GetMapping("/")
    public String ShowIndex(Model model) throws Exception {
        ArrayList<San> sanList = sanRepo.getAllSan();
        model.addAttribute("SanList", sanList);
        return "public/index";
    }

    @GetMapping("/Login")
    public String showLogin(){
        return "public/login";
    }

    @GetMapping("/Logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("UserAfterLogin");
        return "redirect:/";
    }

    @PostMapping("/LoginToSystem")
    public String loginToSystem(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) throws Exception{
        User user = loginRepo.checkLogin(username, password);
        if (user == null) {
            return "public/login";
        } else {
            httpSession.setAttribute("UserAfterLogin", user);
            return "redirect:/";
        }
    }
}