package com.epam.lab.pavel_katsuba.vneklasniki.filters;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.ServiceAccessRelate;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceAccessDao;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.epam.lab.pavel_katsuba.vneklasniki.utils.TokenConverter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter("/api/*")
public class ValidateFilter implements Filter {
    private VneklasnikiDao<Service> serviceDao = new ServiceDao(MySqlDBManager.instance());
    private VneklasnikiDao<ServiceAccessRelate> relateDao = new ServiceAccessDao(MySqlDBManager.instance());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);
        session.setMaxInactiveInterval(600);
        List<String> splitPath = Arrays.asList(httpRequest.getPathInfo().split("/"));
        if (splitPath.contains("registration") && "POST".equals(httpRequest.getMethod())){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = httpRequest.getHeader("token");
        if (token == null) {
            httpResponse.setStatus(401);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }
//       token decryption
        Service tokenService = TokenConverter.decryption(token);
        if (isAdmin(tokenService)) {
            if ("/registration/info".equals(httpRequest.getPathInfo())) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            httpResponse.setStatus(401);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }
        int serviceId = serviceDao.getEntityId(tokenService);
        if (serviceId == Constants.NAN_ID) {
            httpResponse.setStatus(500);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }
        Service service = serviceDao.getEntity(serviceId);
        if (!serviceValidate(service, tokenService.getPassword())) {
            httpResponse.setStatus(400);
            httpRequest.getRequestDispatcher("/info.jsp").forward(httpRequest, httpResponse);
            return;
        }
//        maybe it will be deleted
        List<String> accesses = new ArrayList<>();
        for (ServiceAccessRelate relate : relateDao.getAllEntities()) {
            if (relate.getServiceId() == serviceId) {
                accesses.add(relate.getAccess());
            }
        }
//        Optional<String> first = splitPath.stream()
//                .filter(s -> !isNumeric(s))
//                .findFirst();
        String targetPath;
        if (isNumeric(splitPath.get(splitPath.size() - 1))) {
            if (isNumeric(splitPath.get(splitPath.size() - 2))) {
                targetPath = splitPath.get(splitPath.size() - 3);
            } else targetPath = splitPath.get(splitPath.size() - 2);
        } else targetPath = splitPath.get(splitPath.size() - 1);

//        if (accesses.contains(first.get())) {
        if (accesses.contains(targetPath)) {
            service.setRequestCount(service.getRequestCount() - 1);
            boolean isChanged = serviceDao.putEntity(serviceId, service);
            if (isChanged) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
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

    private boolean isNumeric(String str)
    {
        return str.matches("\\d+");
    }
}
