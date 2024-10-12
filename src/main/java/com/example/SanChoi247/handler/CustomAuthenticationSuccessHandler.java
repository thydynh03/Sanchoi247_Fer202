package com.example.SanChoi247.handler; // Đảm bảo import đúng package của bạn

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Chuyển hướng đến trang đăng nhập sau khi nhập mã OTP thành công
        String redirectUrl = request.getSession().getAttribute("redirectUrl") != null ?
                (String) request.getSession().getAttribute("redirectUrl") : "/";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
