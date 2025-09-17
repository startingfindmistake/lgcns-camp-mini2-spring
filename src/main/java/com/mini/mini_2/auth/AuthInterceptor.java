package com.mini.mini_2.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("VALIDATE FOR REQUEST URL : " + request.getRequestURI());
        
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header : " + authHeader);
            System.out.println("Missing or invalid Authorization header");
            unauthorized(response, "Missing or invalid Authorization header");
            return false;
        }

        String token = authHeader.substring(7);
        if (!tokenService.validateToken(token)) {
            System.out.println("Invalid or expired token");
            unauthorized(response, "Invalid or expired token");
            return false;
        }

        System.out.println("VALIDATE AUTHRIZATION");
        String userId = tokenService.getUserIdFromToken(token);
        request.setAttribute("userId", userId);
        return true;
    }

    private void unauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
