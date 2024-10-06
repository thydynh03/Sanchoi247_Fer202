package com.example.SanChoi247.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SanChoi247.model.entity.LoaiSan;
import com.example.SanChoi247.model.entity.San;
import com.example.SanChoi247.model.entity.Size;
import com.example.SanChoi247.model.repo.LoaiSanRepo;
import com.example.SanChoi247.model.repo.SanRepo;
import com.example.SanChoi247.model.repo.SizeRepo;

@Controller
public class SanController {
    @Autowired
    SanRepo sanRepo;
    @Autowired
    LoaiSanRepo loaiSanRepo;
    @Autowired
    SizeRepo sizeRepo;

    @GetMapping("/ViewDetail/{id}")
    public String viewDetail(@PathVariable("id") int uid, Model model) throws Exception {
        ArrayList<San> sanList = new ArrayList<>();
        sanList = sanRepo.getAllSanByChuSanId(uid);
        model.addAttribute("SanDetail", sanList);
        return "public/viewDeTail";
    }

    // --------------------------------------------------------------------------------------//

    // @GetMapping("/ShowEditStadium/{id}")
    // public String showEditStadium(Model model, @PathVariable("id") int san_id) throws Exception {
    //     ArrayList<LoaiSan> loaiSanList = loaiSanRepo.getAllLoaiSan();
    //     model.addAttribute("LoaiSanList", loaiSanList);
    //     San san = sanRepo.getSanById(san_id);
    //     model.addAttribute("San", san);
    //     return "owner/editStadium";
    // }

    // @PostMapping("/EditStadium")
    // public String EidtStadium(@RequestParam("san_id") int san_id,
    //         @RequestParam("loai_san_id") int loai_san_id,
    //         @RequestParam("vi_tri_san") String vi_tri_san, @RequestParam("size_id") int size_id,
    //         @RequestParam("img") String img) throws Exception {
    //     LoaiSan loaiSan = loaiSanRepo.getLoaiSanById(loai_san_id);
    //     Size size = sizeRepo.getSizeById(size_id);
    //     San san = new San(loaiSan, vi_tri_san, size, img);
    //     sanRepo.save(san);
    //     return "redirect:/";
    // }
}
