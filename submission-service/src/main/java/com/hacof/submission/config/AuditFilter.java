package com.hacof.submission.config;

import java.io.IOException;

import jakarta.servlet.*;

import org.springframework.stereotype.Component;

import com.hacof.submission.util.AuditContext;
import com.hacof.submission.util.SecurityUtil;

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
