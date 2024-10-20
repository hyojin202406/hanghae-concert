package com.hhplu.hhplusconcert.app.common.filter;

import com.hhplu.hhplusconcert.app.application.service.token.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final TokenValidationService tokenValidationService;

    @Bean
    public FilterRegistrationBean<TokenValidationFilter> tokenValidationFilter() {
        FilterRegistrationBean<TokenValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenValidationFilter(tokenValidationService));
        registrationBean.addUrlPatterns(
                "/api/concerts/*",
                "/api/reservations",
                "/api/payments/*"
        );
        return registrationBean;
    }
}