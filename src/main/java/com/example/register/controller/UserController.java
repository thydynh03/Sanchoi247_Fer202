package com.example.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.register.model.entity.UserRegistrationForm;
import com.example.register.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationForm form,
                               BindingResult bindingResult,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi xác thực, trả về trang đăng ký với thông báo lỗi
            return "register";
        }

        // Kiểm tra mật khẩu xác nhận
        if (!form.getPassword().equals(confirmPassword)) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Mật khẩu xác nhận không khớp");
            return "register";
        }

        // Thực hiện đăng ký người dùng
        try {
            userService.registerUser(form);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Bạn có thể đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            // Nếu có lỗi khi đăng ký, trả về trang đăng ký với thông báo lỗi
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi đăng ký: " + e.getMessage());
            return "register";
        }
    }
}
