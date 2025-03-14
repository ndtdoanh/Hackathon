package com.hacof.identity.config;

import java.io.IOException;

import jakarta.servlet.*;

import org.springframework.stereotype.Component;

import com.hacof.identity.util.AuditContext;
import com.hacof.identity.util.SecurityUtil;

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
