package com.dev.javadevproject.dto.auth;

public class JwtResponse {
    public String accessToken;
    public String schemeType = "Bearer";

    public JwtResponse(String jwtToken) {
        this.accessToken = jwtToken;
    }
}
