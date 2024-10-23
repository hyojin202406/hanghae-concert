package com.hhplu.hhplusconcert.config.interceptor;

import com.hhplu.hhplusconcert.app.application.service.token.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class TokenValidationInterceptor implements HandlerInterceptor {

    private final TokenValidationService tokenValidationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("QUEUE-TOKEN");
        if (StringUtils.hasText(token) && tokenValidationService.isValidToken(token)) {
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing token");
            return false;
        }
    }
}