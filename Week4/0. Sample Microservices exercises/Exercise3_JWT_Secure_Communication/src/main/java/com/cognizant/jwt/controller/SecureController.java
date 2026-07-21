package com.cognizant.jwt.controller;

import com.cognizant.jwt.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SecureController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username) {
        String token = jwtTokenProvider.createToken(username);
        return Map.of("token", token);
    }

    @GetMapping("/secure")
    public String secure() {
        return "This is a secure endpoint";
    }
}
