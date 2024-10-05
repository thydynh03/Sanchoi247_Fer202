// package com.example.logingoogle;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> {
//                 auth
//                 .requestMatchers("/", "/favicon.ico", "/auth/login", "/Signup", "/static/**", "/public/index")
//                 .permitAll() // Cho phép truy cập vào các URL này mà không cần xác thực
//                     .anyRequest().authenticated(); // Tất cả các yêu cầu khác cần phải xác thực
//             })
//             .formLogin(form -> 
//                 form
//                     .loginPage("/auth/login") // Đường dẫn tới trang đăng nhập tùy chỉnh
//                     .permitAll() // Cho phép tất cả người dùng truy cập trang đăng nhập
//             )
//             .oauth2Login(oauth2 -> 
//                 oauth2
//                     .loginPage("/auth/login") // Đường dẫn tới trang đăng nhập tùy chỉnh cho OAuth2
//                     .defaultSuccessUrl("/public/index", true) // URL điều hướng sau khi đăng nhập thành công
//             )
//             .logout(logout -> 
//                 logout
//                     .permitAll() // Cho phép tất cả người dùng thực hiện đăng xuất
//             );

//         return http.build(); // Xây dựng và trả về SecurityFilterChain
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails user = User.builder()
//                 .username("admin") // Tên người dùng
//                 .password(passwordEncoder().encode("password")) // Mật khẩu đã được mã hóa
//                 .roles("USER") // Phân quyền cho người dùng
//                 .build();

//         return new InMemoryUserDetailsManager(user); // Trả về một manager chứa người dùng được cấu hình
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(); // Trả về một instance của BCryptPasswordEncoder để mã hóa mật khẩu
//     }
// }
package com.example.logingoogle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/favicon.ico", "/auth/login", "/Signup", "/static/**", "/public/index")
                .permitAll() // Allow access to these URLs without authentication
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(form -> form
                .loginPage("/auth/login") // Custom login page path
                .permitAll() // Allow all users to access the login page
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/auth/login") // Custom OAuth2 login page path
                .defaultSuccessUrl("/public/index", true) // Redirect URL after successful login
            )
            .logout(logout -> logout
                .permitAll() // Allow all users to log out
            );

        return http.build(); // Build and return the SecurityFilterChain
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin") // Username
                .password(passwordEncoder().encode("password")) // Encoded password
                .roles("USER") // Assign role to the user
                .build();

        return new InMemoryUserDetailsManager(user); // Return a manager containing the configured user
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Return an instance of BCryptPasswordEncoder for password encoding
    }
}
