package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.epam.lab.pavel_katsuba.vneklasniki.uti.TokenConverter;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;


@WebServlet("/start")
public class StartController extends HttpServlet {
    private VneklasnikiDao<Service> serviceDao = new ServiceDao(MySqlDBManager.instance());

    @Override
    protected void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String token = (String) httpRequest.getSession().getAttribute("token");
        String[] arrToken = TokenConverter.getArrToken(token);
        int serviceId = serviceDao.getEntityId(new Service(arrToken[0], arrToken[1]));
        Service service = serviceDao.getEntity(serviceId);
        JsonObject object = new JsonObject();
        object.addProperty("login", arrToken[0]);
        object.addProperty("password", arrToken[1]);
        object.addProperty("token", token);
        object.addProperty("status", service.getStatus());
        object.addProperty("requestCount", service.getRequestCount());
        getServletContext().getRequestDispatcher("/info").forward(httpRequest, httpResponse);
    }
}