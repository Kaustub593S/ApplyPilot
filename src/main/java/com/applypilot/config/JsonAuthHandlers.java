package com.applypilot.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Configuration
public class JsonAuthHandlers {

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> write(response, 401, "Authentication required");
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> write(response, 403, "Access denied");
    }

    private static void write(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
