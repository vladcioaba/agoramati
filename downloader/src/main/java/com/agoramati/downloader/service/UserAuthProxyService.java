package com.agoramati.downloader.service;

import com.agoramati.downloader.dto.AuthRequest;
import com.agoramati.downloader.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserAuthProxyService {

    @Value("${user.auth.url}")
    private String userAuthorization;

    public AuthResponse authorizeUser(String token) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(userAuthorization, new AuthRequest(token), AuthResponse.class);
    }

}