package com.cognizant.springlearn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT Authorization Filter.
 *
 * <p>Extends BasicAuthenticationFilter to intercept ALL incoming HTTP requests
 * and validate JWT tokens present in the Authorization header.</p>
 *
 * <p>JWT Authorization Flow:
 * 1. Extract Authorization header from incoming request
 * 2. If header starts with "Bearer ", extract and validate the JWT
 * 3. If token is valid, set the authentication in Spring Security context
 * 4. Pass the request along the filter chain</p>
 *
 * <p>Using a filter (instead of controller) is the correct approach because
 * it intercepts ALL requests, not just specific endpoints.</p>
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    /** Same secret key used to sign JWTs in AuthenticationController. */
    private static final String SECRET_KEY = "secretkey";

    /** JWT Bearer token prefix in Authorization header. */
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Constructor passes the AuthenticationManager to the parent class.
     * Spring Security requires the AuthenticationManager for BasicAuthenticationFilter.
     *
     * @param authenticationManager Spring Security's authentication manager
     */
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        LOGGER.info("Start");
        LOGGER.debug("{}: ", authenticationManager);
    }

    /**
     * Called for every HTTP request passing through this filter.
     *
     * <p>Checks if the Authorization header contains a Bearer token.
     * If not, the request passes through without JWT validation
     * (Spring Security will handle it with Basic Auth or reject it).
     * If a Bearer token is present, validates it and sets authentication.</p>
     *
     * @param req   The incoming HTTP request
     * @param res   The HTTP response
     * @param chain The filter chain to continue processing
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Start");
        String header = req.getHeader("Authorization");
        LOGGER.debug("Authorization header: {}", header);

        // If no Bearer token is present, continue filter chain without JWT validation
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        // Validate the JWT and get authentication object
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        // Set authentication in Spring Security context
        // This marks the current request as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
        LOGGER.info("End");
    }

    /**
     * Validates the JWT token from the Authorization header.
     *
     * <p>JWT Validation Steps:
     * 1. Extract the token from "Bearer <token>"
     * 2. Parse and verify the token signature using the secret key
     * 3. Extract the subject (username) from the token payload
     * 4. Return an authenticated UsernamePasswordAuthenticationToken</p>
     *
     * <p>If the token is invalid, expired, or tampered with,
     * JwtException is caught and null is returned, causing the request
     * to be rejected by Spring Security.</p>
     *
     * @param request The HTTP request containing the Authorization header
     * @return UsernamePasswordAuthenticationToken if valid, null otherwise
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            Jws<Claims> jws;
            try {
                // Parse and validate the JWT, stripping the "Bearer " prefix
                jws = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token.replace(BEARER_PREFIX, ""));

                // Extract the subject (username) from the JWT payload
                String user = jws.getBody().getSubject();
                LOGGER.debug("JWT validated for user: {}", user);

                if (user != null) {
                    // Return an authenticated token with empty authorities list
                    // In production, roles would be embedded in the JWT and extracted here
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            } catch (JwtException ex) {
                // Token is invalid, expired, or tampered with
                LOGGER.warn("JWT validation failed: {}", ex.getMessage());
                return null;
            }
            return null;
        }
        return null;
    }
}
