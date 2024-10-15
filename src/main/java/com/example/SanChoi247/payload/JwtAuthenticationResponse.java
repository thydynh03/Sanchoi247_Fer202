package com.example.SanChoi247.payload;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresIn; // Thời gian token hết hạn (tính bằng milliseconds)

    public JwtAuthenticationResponse(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn; // Khởi tạo giá trị thời gian hết hạn
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
