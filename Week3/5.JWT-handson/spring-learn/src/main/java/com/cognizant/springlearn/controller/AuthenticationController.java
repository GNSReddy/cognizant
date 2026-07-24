package com.cognizant.springlearn.controller;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("Start authenticate");
        LOGGER.debug("Authorization Header: {}", authHeader);

        String user = getUser(authHeader);
        LOGGER.debug("Decoded user: {}", user);

        String token = generateJwt(user);
        LOGGER.debug("Generated JWT Token: {}", token);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.info("End authenticate");
        return response;
    }

    private String getUser(String authHeader) {
        LOGGER.info("Start getUser");
        LOGGER.debug("Header to decode: {}", authHeader);

        // Authorization header format: "Basic <encodedCredentials>"
        String encodedCredentials = authHeader.replace("Basic ", "").trim();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String credentials = new String(decodedBytes);

        // credentials format: "username:password"
        String[] values = credentials.split(":", 2);
        String username = values[0];

        LOGGER.debug("Extracted username: {}", username);
        LOGGER.info("End getUser");
        return username;
    }

    private String generateJwt(String user) {
        LOGGER.info("Start generateJwt");
        LOGGER.debug("User for token: {}", user);

        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user);

        // Set the token issue time as current time
        builder.setIssuedAt(new Date());

        // Set the token expiry as 20 minutes from now (20 * 60 * 1000 = 1,200,000 ms)
        builder.setExpiration(new Date(System.currentTimeMillis() + 1200000));
        
        // Sign with HS256 and key "secretkey"
        builder.signWith(SignatureAlgorithm.HS256, "secretkey");

        String token = builder.compact();

        LOGGER.info("End generateJwt");
        return token;
    }
}
