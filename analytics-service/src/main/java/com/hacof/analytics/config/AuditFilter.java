package com.hacof.analytics.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.springframework.stereotype.Component;

import com.hacof.analytics.util.AuditContext;
import com.hacof.analytics.util.SecurityUtil;

@Component
public class AuditFilter implements Filter {

    private final SecurityUtil securityUtil;

    public AuditFilter(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            securityUtil.setAuditUser();
            chain.doFilter(request, response);
        } finally {
            AuditContext.clear();
        }
    }
}
