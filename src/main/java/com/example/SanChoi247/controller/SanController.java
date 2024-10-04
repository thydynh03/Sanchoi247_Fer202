package com.example.SanChoi247.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.SanChoi247.model.entity.San;
import com.example.SanChoi247.model.repo.SanRepo;

@Controller
public class SanController {
    @Autowired
    SanRepo sanRepo;

    @GetMapping("/ViewDetail/{id}")
    public String viewDetail(@PathVariable("id") int uid, Model model) throws Exception {
        ArrayList<San> sanList = new ArrayList<>();
        sanList = sanRepo.getAllSanByChuSanId(uid);
        model.addAttribute("SanDetail", sanList);
        return "public/viewDeTail";
    }
}
