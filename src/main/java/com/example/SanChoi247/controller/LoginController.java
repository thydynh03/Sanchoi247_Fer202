package com.example.SanChoi247.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.SanChoi247.model.dto.SignUpRequest;
import com.example.SanChoi247.model.entity.User;
import com.example.SanChoi247.model.repo.LoginRepo;
import com.example.SanChoi247.model.repo.UserRepo;
import com.example.SanChoi247.service.SendOtpToMailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    LoginRepo loginRepo;
    @Autowired
    private SendOtpToMailService sendOtpToMailService;

    private String generatedOtp;  // Biến để lưu OTP
    private String email; // Lưu email để sử dụng cho gửi lại OT

    @GetMapping("/")
    public String ShowIndex(Model model, HttpServletRequest httpServletRequest) throws Exception {
        ArrayList<User> sanList = userRepo.getAllUser();
        ArrayList<User> Owner = new ArrayList<>();
        for (User user : sanList) {
            if (user.getRole() == 'C') {
                Owner.add(user);
            }
        }
        model.addAttribute("userList", Owner);
        return "public/index";
    }

    @GetMapping("/Login")
    public String showLogin(Model model) throws Exception {
        return "auth/login";
    }

    @GetMapping("/Logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("UserAfterLogin");
        httpSession.invalidate();
        return "redirect:/";
    }

    @PostMapping("/LoginToSystem")
    public String LoginToSystem(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpSession httpSession, Model model) throws Exception {
        User user = loginRepo.checkLogin(username, password);
        if (user == null) {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        } else {
            httpSession.setAttribute("UserAfterLogin", user);
            return "redirect:/";
        }
    }

    @GetMapping("/Signup")
    public String showSignUpForm() {
        return "auth/login"; // Trả về trang đăng ký
    }

    @PostMapping("/Signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest, HttpSession httpSession) throws Exception {
        String email = signUpRequest.getEmail();
        String name = signUpRequest.getName();
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        String confirmPassword = signUpRequest.getConfirmPassword();
        char role = 'U'; // Default role
        char gender = signUpRequest.getGender(); // Get first character (M or F)
        
    
    // Check for existing username
    if (userRepo.existsByUsername(username)) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Username already exists."));
    }

    // Check for existing email
    if (userRepo.existsByEmail(email)) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Email already exists."));
    }

    // Validate password
    if (!userRepo.isValidPassword(password)) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Password invalid."));
    }

// Check if passwords match
System.out.println("Password: " + password);
System.out.println("Confirm Password: " + confirmPassword);
    if (!userRepo.isValidPassword(password)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password invalid.");
    }


    User newUser = new User();
    newUser.setName(name);
    newUser.setEmail(email);
    newUser.setUsername(username);
    newUser.setRole(Character.toUpperCase(role));
    newUser.setGender(gender); // Store the gender
    // Store user in session
    System.out.println("New User before saving: " + newUser);
    
    // Validate newUser fields
    if (newUser.getName() == null || newUser.getEmail() == null || newUser.getUsername() == null) {
        throw new Exception("Name, Email, or Username cannot be null.");
    }

    // Store user in session
    httpSession.setAttribute("newUser", newUser);


    // Send OTP via email
    try {
        generatedOtp = sendOtpToMailService.sendOtpService(email);
        return ResponseEntity.ok(new SuccessResponse("OTP sent to your email.", "auth/enterOtp"));
        
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}

// ErrorResponse and SuccessResponse classes for structured responses
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

public class SuccessResponse {
    private String message;
    private String redirectUrl;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public SuccessResponse(String message, String redirectUrl) {
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}

// Check password match in userRepo
public boolean isPasswordMatching(SignUpRequest signUpRequest) {
    String password = signUpRequest.getPassword();
    String confirmPassword = signUpRequest.getConfirmPassword();
    return password != null && password.equals(confirmPassword);
}

    

    @PostMapping("/resendOtp")
    public String resendOtp(Model model) {
        try {
            generatedOtp = sendOtpToMailService.sendOtpService(email); // Gửi lại OTP
            model.addAttribute("success", "OTP đã được gửi lại.");
            model.addAttribute("email", email);
            return "auth/enterOtp"; // Chuyển hướng đến trang nhập OTP
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login"; // Quay lại trang nhập OTP
        }
    }
    @GetMapping("/auth/enterOtp")
public String enterOtpPage() {
    return "auth/enterOtp"; // This should point to your template/view file
}

@PostMapping("/verifyOtp")
public String verifyOtp(@RequestParam("otp") String otp, HttpSession httpSession, Model model) throws Exception {
    User newUser = (User) httpSession.getAttribute("newUser"); // Lấy thông tin người dùng từ session
    if (newUser == null) {
        model.addAttribute("error", "User session expired. Please sign up again.");
        return "auth/enterOtp"; // Quay lại trang nhập OTP nếu session đã hết hạn
    }

    // Kiểm tra OTP
    if (otp == null || otp.trim().isEmpty()) {
        model.addAttribute("error", "OTP không được để trống.");
        return "auth/enterOtp"; // Quay lại trang nhập OTP nếu OTP rỗng
    }

    if (!generatedOtp.equals(otp)) {
        model.addAttribute("error", "OTP không chính xác."); 
        return "auth/enterOtp"; // Quay lại trang nhập OTP nếu OTP không đúng
    }

    // OTP hợp lệ, tiến hành lưu user vào database
    try {
        userRepo.addNewUser(newUser);
        System.out.println("User saved successfully: " + newUser);
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("error", "Error saving user: " + e.getMessage());
        return "auth/enterOtp"; // Quay lại trang nhập OTP nếu có lỗi
    }

    // Xóa session sau khi hoàn thành đăng ký
    httpSession.invalidate();
    httpSession.setAttribute("redirectUrl", "/auth/login"); // Đường dẫn chuyển hướng
    return "redirect:/auth/login"; // Chuyển hướng về trang đăng nhập
}

}
