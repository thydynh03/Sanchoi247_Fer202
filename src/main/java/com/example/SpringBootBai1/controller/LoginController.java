package com.example.SpringBootBai1.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringBootBai1.model.entity.User;
import com.example.SpringBootBai1.model.repo.LoginRepo;
import com.example.SpringBootBai1.model.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private UserRepo userRepo;

    // Hiển thị trang chủ
    @GetMapping("/")
    public String showIndex(HttpSession session, Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "public/index";
    }

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLogin() {
        return "public/login";
    }

    // Xử lý đăng nhập
    @PostMapping("/LoginToSystem")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        try {
            User user = loginRepo.checkLogin(username, password);
            if (user != null) {
                session.setAttribute("UserAfterLogin", user);
                return "redirect:/viewProfile"; // Redirect to viewProfile after login
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "public/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during login");
            return "public/login";
        }
    }

    // Xử lý đăng xuất
    @GetMapping("/Logout")
    public String logout(HttpSession session) {
        session.removeAttribute("UserAfterLogin");
        return "redirect:/";
    }

    // Hiển thị form đăng ký
    @GetMapping("/signup")
    public String showSignup() {
        return "public/signup";
    }

    // Xử lý đăng ký người dùng mới
    @PostMapping("/signupuser")
    public String signupUser(
        @RequestParam("name") String name,
        @RequestParam("dob") String dob,
        @RequestParam("gender") String gender,
        @RequestParam("phone") String phone,
        @RequestParam("email") String email,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("cpassword") String confirmPassword,
        @RequestParam("role") String role,
        HttpSession session) {

        // Kiểm tra khớp mật khẩu
        if (!password.equals(confirmPassword)) {
            return "redirect:/signup?error=Passwords do not match";
        }

        try {
            // Validate ngày sinh
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateOfBirth = dateFormat.parse(dob);
            java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getTime());

            // Chuyển đổi role thành 'U' cho User và 'C' cho Field Owner
            char roleChar;
            if (role.equals("User")) {
                roleChar = 'U';
            } else if (role.equals("Field Owner")) {
                roleChar = 'C';
            } else {
                return "redirect:/signup?error=Invalid role selection";
            }

            // Chuyển đổi giới tính thành 'M' cho Male và 'F' cho Female
            char genderChar;
            if (gender.equals("Male")) {
                genderChar = 'M';
            } else if (gender.equals("Female")) {
                genderChar = 'F';
            } else {
                return "redirect:/signup?error=Invalid gender selection";
            }

            // Tạo và lưu đối tượng User mới
            User user = new User(name, sqlDateOfBirth, genderChar, phone, email, username, password, roleChar);
            userRepo.addNewUser(user);

        } catch (ParseException e) {
            return "redirect:/signup?error=Invalid date format";
        } catch (Exception e) {
            return "redirect:/signup?error=An error occurred";
        }

        return "redirect:/login";
    }

    // Hiển thị trang hồ sơ người dùng
    @GetMapping("/viewProfile")
    public String viewProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "public/viewProfile";
    }

    // Hiển thị form đổi mật khẩu
    @GetMapping("/ChangePassword")
    public String showChangePasswordForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "public/change_password";
    }

    // Xử lý thay đổi mật khẩu
    @PostMapping("/ChangePassword")
    public String changePassword(HttpSession session, 
                                 @RequestParam("newPassword") String newPassword, 
                                 Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            userRepo.changePassword(user.getUid(), newPassword);
            model.addAttribute("message", "Password changed successfully");
            session.removeAttribute("UserAfterLogin");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "public/change_password";
        }
    }
}
