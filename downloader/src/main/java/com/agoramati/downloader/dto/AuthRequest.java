package com.agoramati.downloader.dto;

public class AuthRequest {

    private String token;

    public AuthRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}