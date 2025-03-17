package com.hacof.communication.config;

import com.hacof.communication.util.AuditContext;
import com.hacof.communication.util.SecurityUtil;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
