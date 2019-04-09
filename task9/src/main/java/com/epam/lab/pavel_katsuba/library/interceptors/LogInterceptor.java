package com.epam.lab.pavel_katsuba.library.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = LogManager.getLogger(LogInterceptor.class.getSimpleName());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("Request URL: " + request.getRequestURL());
        log.info("Start Time: " + System.currentTimeMillis());
        log.info("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("login: " + request.getAttribute("username"));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("Request URL: " + request.getRequestURL());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("Request URL: " + request.getRequestURL());
        log.info("End Time: " + System.currentTimeMillis());
        log.info("Principal: " + SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }

}