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

    // Display the home page
    @GetMapping("/")
    public String showIndex(HttpSession session, Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "public/index";
    }

    // Display the login page
    @GetMapping("/login")
    public String showLogin() {
        return "public/login";
    }

    // Handle login process
    @PostMapping("/LoginToSystem")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        try {
            User user = loginRepo.checkLogin(username, password);
            if (user != null) {
                session.setAttribute("UserAfterLogin", user);
                return "redirect:/viewProfile"; // Redirect to profile page after successful login
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "public/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during login");
            return "public/login";
        }
    }

    // Handle logout process
    @GetMapping("/Logout")
    public String logout(HttpSession session) {
        session.removeAttribute("UserAfterLogin");
        return "redirect:/";
    }

    // Display the signup form
    @GetMapping("/signup")
    public String showSignup() {
        return "public/signup";
    }

    // Handle new user registration
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

        // Password matching validation
        if (!password.equals(confirmPassword)) {
            return "redirect:/signup?error=Passwords do not match";
        }

        // Password strength validation
        if (!isPasswordStrong(password)) {
            return "redirect:/signup?error=Password is too weak";
        }

        try {
            // Date of Birth validation
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateOfBirth = dateFormat.parse(dob);
            java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getTime());

            // Convert role to 'U' for User and 'C' for Field Owner
            char roleChar;
            if (role.equals("User")) {
                roleChar = 'U';
            } else if (role.equals("Field Owner")) {
                roleChar = 'C';
            } else {
                return "redirect:/signup?error=Invalid role selection";
            }

            // Convert gender to 'M' for Male and 'F' for Female
            char genderChar;
            if (gender.equals("Male")) {
                genderChar = 'M';
            } else if (gender.equals("Female")) {
                genderChar = 'F';
            } else {
                return "redirect:/signup?error=Invalid gender selection";
            }

            // Create and save the new User object
            User user = new User(name, sqlDateOfBirth, genderChar, phone, email, username, password, roleChar);
            userRepo.addNewUser(user);

        } catch (ParseException e) {
            return "redirect:/signup?error=Invalid date format";
        } catch (Exception e) {
            return "redirect:/signup?error=An error occurred";
        }

        return "redirect:/login";
    }

    // Method to check password strength
    private boolean isPasswordStrong(String password) {
        // Password must be at least 8 characters, contain uppercase, lowercase, number, and special character
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*\\d.*")) return false;
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) return false;
        return true;
    }

    // Display user profile page
    @GetMapping("/viewProfile")
    public String viewProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "public/viewProfile";
    }

    // Display change password form
    @GetMapping("/ChangePassword")
    public String showChangePasswordForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "public/change_password";
    }

    // Handle password change
    @PostMapping("/ChangePassword")
    public String changePassword(HttpSession session, 
                                 @RequestParam("newPassword") String newPassword, 
                                 Model model) {
        User user = (User) session.getAttribute("UserAfterLogin");
       
        if (user == null) {
            return "redirect:/login";
        }
        try {
            // Check if the new password is strong
            if (!isPasswordStrong(newPassword)) {
                model.addAttribute("error", "Password is too weak. It must contain at least 8 characters, including uppercase, lowercase, numbers, and special characters.");
                return "public/change_password";
            }

            // Change the password in the database
            userRepo.changePassword(user.getUid(), newPassword);
            model.addAttribute("message", "Password changed successfully");

            // Remove user from session after password change
            session.removeAttribute("UserAfterLogin");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "public/change_password";
        }
    }
}
