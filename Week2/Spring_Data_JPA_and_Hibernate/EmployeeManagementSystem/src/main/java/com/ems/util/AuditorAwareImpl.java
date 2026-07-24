package com.ems.util;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // In a real application with Spring Security, you would extract the username from the SecurityContext
        return Optional.of("admin_user");
    }
}
