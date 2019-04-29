package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(getServletContext().getContextPath() + Constants.INFO_PAGE);
    }
}
