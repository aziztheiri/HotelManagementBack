package com.aziz.booksocialnetwork.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Get the origin header from the request
        String originHeader = request.getHeader("origin");

        // Set CORS headers on the response
        response.setHeader("Access-Control-Allow-Origin", originHeader); // Allow requests from the provided origin
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"); // Allowed HTTP methods
        response.setHeader("Access-Control-Max-Age", "3600"); // Cache the CORS preflight response for 1 hour
        response.setHeader("Access-Control-Allow-Headers", "*"); // Allow all headers

        // Check if the request is a preflight OPTIONS request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            // For preflight requests, respond with HTTP 200 OK and stop further processing
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Proceed with the filter chain for other requests
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
