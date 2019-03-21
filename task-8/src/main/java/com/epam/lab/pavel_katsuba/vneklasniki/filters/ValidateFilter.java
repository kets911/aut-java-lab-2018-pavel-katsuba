package com.epam.lab.pavel_katsuba.vneklasniki.filters;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.ServiceAccessRelate;
import com.epam.lab.pavel_katsuba.vneklasniki.controllers.StartController;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.AccessDao;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceAccessDao;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.epam.lab.pavel_katsuba.vneklasniki.uti.TokenConverter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter("/api/*")
public class ValidateFilter implements Filter {
    private VneklasnikiDao<Service> serviceDao = new ServiceDao(MySqlDBManager.instance());
    private VneklasnikiDao<String> accessDao = new AccessDao(MySqlDBManager.instance());
    private VneklasnikiDao<ServiceAccessRelate> relateDao = new ServiceAccessDao(MySqlDBManager.instance());
    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        List<String> splitPath = Arrays.asList(httpRequest.getPathInfo().split("/"));
        System.out.println("Path ->" + splitPath);
        if (splitPath.contains("registration") && "POST".equals(httpRequest.getMethod())){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token;
        if ("start".equals(httpRequest.getPathInfo())) {
            token = (String) httpServletRequest.getSession().getAttribute("token");
        } else token = httpRequest.getHeader("token");


        System.out.println("token -> " + token);
        if (token == null) {
            httpResponse.setStatus(401);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }
//       token decryption

        String[] splitToken = TokenConverter.getArrToken(token);
        Service service = new Service(splitToken[0], splitToken[1]);
        if (!serviceValidate(service, splitToken[1])) {
            httpResponse.setStatus(400);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }

        if (isAdmin(service) && splitPath.contains("info")) {
            if (splitPath.contains("info")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            httpResponse.setStatus(401);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }

        LocalDateTime tokenCreation = LocalDateTime.parse(splitToken[2]);
        LocalDateTime now = LocalDateTime.now();
        long between = ChronoUnit.MINUTES.between(tokenCreation, now);

        System.out.println(between);
        if (between > 10) {
            httpResponse.setStatus(307);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }

        int serviceId = serviceDao.getEntityId(service);
        if (serviceId == Constants.NAN_ID) {
            httpResponse.setStatus(500);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }
        service = serviceDao.getEntity(serviceId);
        List<String> accesses = new ArrayList<>();
        for (ServiceAccessRelate relate : relateDao.getAllEntities()) {
            if (relate.getServiceId() == serviceId) {
                accesses.add(relate.getAccess());
            }
        }
        service.setAccess(accesses);

        System.out.println(service);
//        ^ token decryption
        List<String> access = service.getAccess();
        for (String string : splitPath) {
            if (access.contains(string)) {
               service.setRequestCount(service.getRequestCount() - 1);
                boolean isChanged = serviceDao.putEntity(serviceId, service);
                if (isChanged) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        httpResponse.setStatus(403);
        httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
    }

    private boolean serviceValidate(Service service, String pass) {
        return service.getPassword().equals(pass);
    }

    private boolean isAdmin(Service service) {
        String login = service.getLogin().toLowerCase();
        String pass = service.getPassword().toLowerCase();
        return "admin".equals(login) && "password".equals(pass);
    }
}
