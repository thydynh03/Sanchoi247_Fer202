package com.example.SanChoi247.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.example.SanChoi247.model.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component  // Thêm annotation này  

public class JwtTokenProvider {
    // Chuỗi secret key do bạn tự tạo, cần đảm bảo độ dài phù hợp cho thuật toán HS512
    private final String SECRET_KEY = "sanchoi247SecureKeyForJWTSigningXyZ123456789abcdefghijklmnopqrstuv";
    private final long JWT_EXPIRATION = 604800000L; // 7 ngày

    // Tạo SecretKey từ chuỗi bí mật
    private SecretKey createSecretKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8); // Chuyển chuỗi thành mảng byte
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512"); // Tạo SecretKey cho thuật toán HS512
    }

    // Tạo token cho người dùng
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(createSecretKey(), SignatureAlgorithm.HS512)  // Đảm bảo createSecretKey trả về đúng SecretKey cho HS512
        .compact();
    }

    // Lấy secret key (nếu cần thiết ở những chỗ khác)
    public SecretKey getSecretKey() {
        return createSecretKey();
    }
}
