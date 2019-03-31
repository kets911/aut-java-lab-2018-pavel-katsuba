package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.epam.lab.pavel_katsuba.vneklasniki.utils.TokenConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthorizationController extends HttpServlet {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String LOGIN_NOT_EXIST = "Login %s isn't exist";
    public static final String WRONG_PASSWORD = "Wrong password";
    private DBManager dbManager = MySqlDBManager.instance();
    private VneklasnikiDao<Service> serviceDao = new ServiceDao(dbManager);

    @Override
    public void init() throws ServletException {
        super.init();
        Service admin = new Service("admin", PASSWORD);
        if (serviceDao.getEntityId(admin) == Constants.NAN_ID) {
            serviceDao.create(admin);
        }
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String token = (String) httpRequest.getSession().getAttribute(Constants.TOKEN);
        Service tokenService = TokenConverter.decryption(token);
        int serviceId = serviceDao.getEntityId(tokenService);
        Service service = serviceDao.getEntity(serviceId);
        if (!service.getPassword().equals(tokenService.getPassword())) {
            httpResponse.setStatus(400);
        } else {
            httpRequest.setAttribute(Constants.SERVICE, service);
            httpRequest.setAttribute(Constants.TOKEN, token);
        }
        getServletContext().getRequestDispatcher(Constants.INFO_PAGE).forward(httpRequest, httpResponse);
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String login = httpRequest.getParameter(LOGIN);
        String password = httpRequest.getParameter(PASSWORD);
        int serviceId = serviceDao.getEntityId(new Service(login, password));
        if (serviceId == Constants.NAN_ID) {
            httpResponse.setStatus(400);
            httpRequest.setAttribute(Constants.ERROR_MESSAGE, String.format(LOGIN_NOT_EXIST, login));
            getServletContext().getRequestDispatcher(Constants.INFO_PAGE).forward(httpRequest, httpResponse);
            return;
        }
        Service service = serviceDao.getEntity(serviceId);
        if (!service.getPassword().equals(password)) {
            httpResponse.setStatus(400);
            httpRequest.setAttribute(Constants.ERROR_MESSAGE, WRONG_PASSWORD);
            getServletContext().getRequestDispatcher(Constants.INFO_PAGE).forward(httpRequest, httpResponse);
            return;
        }
        String token = TokenConverter.getToken(login, password);
        HttpSession session = httpRequest.getSession();
        session.setAttribute(Constants.TOKEN, token);
        httpRequest.setAttribute(Constants.SERVICE, service);
        httpRequest.setAttribute(Constants.TOKEN, token);
        getServletContext().getRequestDispatcher(Constants.INFO_PAGE).forward(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
        super.destroy();
        dbManager.destroy();
    }
}