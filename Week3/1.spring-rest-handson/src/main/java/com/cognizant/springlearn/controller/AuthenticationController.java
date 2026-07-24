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

/**
 * REST Controller for JWT Authentication.
 *
 * <p>Handles Step 1 and 2 of the JWT Process Flow:
 * 1. Receives username/password via HTTP Basic (Authorization: Basic base64(user:pwd))
 * 2. Validates credentials via Spring Security (configured in SecurityConfig)
 * 3. Decodes the Authorization header to extract the username
 * 4. Generates a JWT token signed with HS256 algorithm
 * 5. Returns the token as {"token": "..."}</p>
 *
 * <p>URL: GET /authenticate
 * Authorization: Basic base64(username:password)</p>
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    /** Secret key used to sign and verify JWT tokens. */
    private static final String SECRET_KEY = "secretkey";

    /** Token expiry time in milliseconds (20 minutes = 1200000 ms). */
    private static final long TOKEN_EXPIRY_MS = 1200000L;

    /**
     * Authenticates user via HTTP Basic and returns a JWT token.
     *
     * <p>Spring automatically reads the Authorization header value and passes
     * it as the authHeader parameter. The URL is secured in SecurityConfig
     * to require USER or ADMIN role, so Spring Security has already
     * validated the credentials before this method is called.</p>
     *
     * @param authHeader The Authorization header value (e.g., "Basic dXNlcjpwd2Q=")
     * @return Map containing the generated JWT token with key "token"
     */
    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("Start");
        LOGGER.debug("Authorization header received: {}", authHeader);

        // Decode the Base64-encoded credentials and extract the username
        String user = getUser(authHeader);
        LOGGER.debug("Authenticated user: {}", user);

        // Generate JWT token for the authenticated user
        String token = generateJwt(user);
        LOGGER.debug("Generated JWT token for user: {}", user);

        // Return token in a map (serialized as JSON: {"token": "..."})
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.info("End");
        return response;
    }

    /**
     * Extracts the username from the HTTP Basic Authorization header.
     *
     * <p>HTTP Basic Authorization header format:
     * "Basic " + Base64(username:password)
     * Example: "Basic dXNlcjpwd2Q=" decodes to "user:pwd"</p>
     *
     * @param authHeader Raw Authorization header value
     * @return Extracted username from decoded credentials
     */
    private String getUser(String authHeader) {
        LOGGER.info("Start");
        LOGGER.debug("Decoding Authorization header");

        // Strip the "Basic " prefix to get only the Base64-encoded part
        String encodedCredentials = authHeader.substring("Basic ".length());
        LOGGER.debug("Encoded credentials: {}", encodedCredentials);

        // Decode Base64 encoded string to byte array, then convert to String
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes);
        LOGGER.debug("Decoded credentials: {}", decodedCredentials);

        // Extract the username (text before the colon separator)
        String user = decodedCredentials.substring(0, decodedCredentials.indexOf(':'));
        LOGGER.debug("Extracted user: {}", user);

        LOGGER.info("End");
        return user;
    }

    /**
     * Generates a signed JWT token for the given user.
     *
     * <p>JWT Structure:
     * - Header: {"alg": "HS256"} (algorithm used for signing)
     * - Payload: {"sub": username, "iat": issuedAt, "exp": expiration}
     * - Signature: HMAC-SHA256(header.payload, secretkey)</p>
     *
     * <p>Token expiry is set to 20 minutes from the current time.
     * signWith() uses HS256 symmetric algorithm with the secret key.</p>
     *
     * @param user Username to embed in the JWT subject claim
     * @return Compact JWT token string (header.payload.signature)
     */
    private String generateJwt(String user) {
        LOGGER.info("Start");
        LOGGER.debug("Generating JWT for user: {}", user);

        JwtBuilder builder = Jwts.builder();

        // Subject claim identifies the principal this token is about (the user)
        builder.setSubject(user);

        // Set the token issue time as current time (iat claim)
        builder.setIssuedAt(new Date());

        // Set the token expiry as 20 minutes from now (exp claim)
        builder.setExpiration(new Date((new Date()).getTime() + TOKEN_EXPIRY_MS));

        // Sign the token with HS256 algorithm using the secret key
        builder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        // Compact the builder into the final JWT string
        String token = builder.compact();
        LOGGER.debug("JWT generated successfully");
        LOGGER.info("End");
        return token;
    }
}
