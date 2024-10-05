package com.example.logingoogle;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller; // Use Controller instead of RestController for serving views
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller // Change RestController to Controller for rendering HTML views
public class MyController {
    private final ClientRegistrationRepository clientRegistrationRepository;

    public MyController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/auth/login")
    public String login(Model model) {
        // Get the Google login URL from the ClientRegistrationRepository
        String googleLoginUrl = clientRegistrationRepository.findByRegistrationId("google").getProviderDetails().getAuthorizationUri();
        model.addAttribute("googleLoginUrl", googleLoginUrl);
        return "auth/login"; // Đường dẫn tới login.html
    }

    // Handle GET requests to the root URL and redirect to the login page
    @GetMapping("/") 
    public String home() {
        return "redirect:/login"; // Assuming your login page is served at /login
    }

    // Serve the login page
    @GetMapping("/login") // Đổi thành /login
    public String login() {
        return "auth/login"; // Đường dẫn tới login.html
    }

    @GetMapping("/public/index")
    public String index() {
        return "public/index"; // Đường dẫn tới index.html
    }
@GetMapping("/public/UserAfterLogin")
public String afterLoginWithGG(HttpServletRequest request, Authentication authentication) {
    // Set the session attribute if authentication is successful
    if (authentication != null) {
        HttpSession session = request.getSession();
        session.setAttribute("UserAfterLogin", authentication.getPrincipal());
    }
    // Redirect to the index page
    return "redirect:/public/index"; // Redirect to index.html
}
    

    // Test endpoint to confirm the server is running
    @GetMapping("/test")
    public String test() {
        return "Hello, I am test's class";
    }
}
