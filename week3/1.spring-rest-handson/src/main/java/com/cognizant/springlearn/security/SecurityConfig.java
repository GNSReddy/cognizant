package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security Configuration.
 *
 * <p>@Configuration marks this as a Spring configuration class (replaces XML config).
 * @EnableWebSecurity enables Spring Security's web security support and provides
 * the Spring MVC integration.</p>
 *
 * <p>Extends WebSecurityConfigurerAdapter to override the default security configuration.
 * Two configure() methods are overridden:
 * 1. configure(AuthenticationManagerBuilder) - defines in-memory users and roles
 * 2. configure(HttpSecurity) - defines URL authorization rules and JWT filter</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Configures in-memory authentication with two users:
     * - admin (role: ADMIN) with password 'pwd'
     * - user  (role: USER)  with password 'pwd'
     *
     * IMPORTANT NOTE: Hard-coded credentials are for learning purposes only.
     * In production, credentials should be validated from a database.
     *
     * @param auth AuthenticationManagerBuilder to configure in-memory users
     * @throws Exception if configuration fails
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("Start - Configuring in-memory authentication");
        auth.inMemoryAuthentication()
            .withUser("admin").password(passwordEncoder().encode("pwd")).roles("ADMIN")
            .and()
            .withUser("user").password(passwordEncoder().encode("pwd")).roles("USER");
        LOGGER.info("End");
    }

    /**
     * BCryptPasswordEncoder bean for secure password hashing.
     * BCrypt is a strong one-way hashing algorithm; the same password
     * produces different hashes each time, providing extra security.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start");
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures HTTP security:
     * - Disables CSRF (not needed for stateless REST APIs)
     * - Enables HTTP Basic authentication (for /authenticate endpoint)
     * - Allows /authenticate for both USER and ADMIN roles
     * - Requires authentication for all other requests
     * - Adds JwtAuthorizationFilter to validate Bearer tokens on each request
     *
     * <p>JWT Flow:
     * 1. Client sends credentials to /authenticate (via HTTP Basic)
     * 2. Server returns JWT token
     * 3. Client sends JWT in Authorization: Bearer <token> header for all other requests
     * 4. JwtAuthorizationFilter validates the token on each request</p>
     *
     * @param httpSecurity HttpSecurity to configure
     * @throws Exception if configuration fails
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        LOGGER.info("Start - Configuring HTTP security");
        httpSecurity.csrf().disable()
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthorizationFilter(authenticationManager()));
        LOGGER.info("End");
    }
}
