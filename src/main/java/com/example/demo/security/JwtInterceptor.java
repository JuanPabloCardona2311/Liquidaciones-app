package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, 
                            HttpServletResponse response, 
                            Object handler) throws Exception {
        
        // Permitir login sin token
        if (request.getRequestURI().equals("/api/auth/login") || 
            request.getRequestURI().equals("/login") ||
            request.getRequestURI().equals("/") ||
            request.getRequestURI().startsWith("/css/") ||
            request.getRequestURI().startsWith("/js/")) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token no proporcionado\"}");
            return false;
        }

        String token = authHeader.substring(7);
        
        if (!jwtService.isTokenValid(token)) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido\"}");
            return false;
        }

        return true;
    }
}
