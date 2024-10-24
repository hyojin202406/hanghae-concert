package com.hhplu.hhplusconcert.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        try {
            logRequestDetails(cachingRequestWrapper);

            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        } finally {
            logResponseDetails(cachingResponseWrapper);

            cachingResponseWrapper.copyBodyToResponse();
        }
    }

    private void logRequestDetails(ContentCachingRequestWrapper request) throws IOException {
        String requestBody = new String(request.getContentAsByteArray(), request.getCharacterEncoding());
        log.info("Request: [{}] {} - Body: {}", request.getMethod(), request.getRequestURI(), requestBody);
    }

    private void logResponseDetails(ContentCachingResponseWrapper response) throws IOException {
        String responseBody = new String(response.getContentAsByteArray(), response.getCharacterEncoding());
        log.info("Response: [{}] {} - Body: {}", response.getStatus(), response.getContentType(), responseBody);
    }
}