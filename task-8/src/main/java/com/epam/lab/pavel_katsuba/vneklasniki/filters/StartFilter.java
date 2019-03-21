//package com.epam.lab.pavel_katsuba.vneklasniki.filters;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/start")
//public class StartFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpSession session = httpRequest.getSession();
//        session.setMaxInactiveInterval(600);
//        String token = (String) session.getAttribute("token");
//        System.out.println("session is token ->" + token);
//        if (token == null) {
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/info.jsp");
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//}
