package com.epam.lab.pavel_katsuba.vneklasniki.controllers;


import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.ServiceAccessRelate;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.AccessDao;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceAccessDao;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.ServiceDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.JsonConverter;
import com.epam.lab.pavel_katsuba.vneklasniki.uti.PasswordGenerator;
import com.epam.lab.pavel_katsuba.vneklasniki.uti.TokenConverter;
import com.google.gson.JsonObject;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationController {
    private static JsonConverter converter = new JsonConverter();
    private VneklasnikiDao<Service> serviceDao = new ServiceDao(MySqlDBManager.instance());
    private VneklasnikiDao<String> accessDao = new AccessDao(MySqlDBManager.instance());
    private VneklasnikiDao<ServiceAccessRelate> relateDao = new ServiceAccessDao(MySqlDBManager.instance());
    @Context
    private HttpServletRequest httpRequest;

    @PostConstruct
    public void init() {
        Service admin = new Service("admin", "password", null, 0);
        System.out.println("PostConstruct admin -> " + admin);
        if (serviceDao.getEntityId(admin) == Constants.NAN_ID) {
            serviceDao.create(admin);
        }
//        token must be add to pullRequest
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authorization(String json) {
        Service service = converter.fromJson(json, Service.class);
        String login = service.getLogin();
        String password = service.getPassword();
        int serviceId = serviceDao.getEntityId(service);
        if (serviceId == Constants.NAN_ID) {
            return Response.status(400).build();
        }
        service = serviceDao.getEntity(serviceId);
        if (!service.getPassword().equals(password)) {
            return Response.status(400).build();
        }
        String token = TokenConverter.getToken(login, password);
        HttpSession session = httpRequest.getSession();
        session.setAttribute("token", token);
        JsonObject object = new JsonObject();
        object.addProperty("login", login);
        object.addProperty("password", password);
        object.addProperty("token", token);
        object.addProperty("status", service.getStatus());
        object.addProperty("requestCount", service.getRequestCount());
        return Response.ok(object.toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registration(String json) {
        System.out.println(json);
        Service service = converter.fromJson(json, Service.class);
        String login = service.getLogin();
        String password = PasswordGenerator.generate(login);
        service.setPassword(password);
        service.setStatus("standard");
        service.setRequestCount(10);
        int serviceId = serviceDao.getEntityId(service);
        if (serviceId > 0) {
            return Response.status(400).entity(String.format("service %s already exist", login)).build();
        }
        serviceId = serviceDao.create(service);
        for (String access : service.getAccess()) {
            int accessId = accessDao.getEntityId(access);
            if (accessId == Constants.NAN_ID) {
                accessId = accessDao.create(access);
            }
            relateDao.create(new ServiceAccessRelate(service, access, serviceId, accessId));
        }
        String token = TokenConverter.getToken(login, password);
        return Response.ok(getAnswer(token, service, serviceId)).entity(service).build();
    }

    private String getAnswer(String token, Service service, int serviceId) {
        JsonObject object = new JsonObject();
        object.addProperty("token", token);
        object.addProperty("password", service.getPassword());
        object.addProperty("id", serviceId);
        return object.toString();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeService(@PathParam("id") int id, String json) {
        Service service = converter.fromJson(json, Service.class);
        boolean isChanged = serviceDao.putEntity(id, service);
        if (isChanged) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @PUT
    @Path("/upgrade")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeStatus(String json) {
        Service service = converter.fromJson(json, Service.class);
        int serviceId = serviceDao.getEntityId(service);
        if (serviceId == Constants.NAN_ID) {
            return Response.status(400).build();
        }
        String password = service.getPassword();
        service = serviceDao.getEntity(serviceId);
        if (!service.getPassword().equals(password)) {
            return Response.status(400).build();
        }
        service.setStatus("Premium");
        service.setRequestCount(service.getRequestCount() + 90);
        boolean isChanged = serviceDao.putEntity(serviceId, service);
        if (isChanged) {
            return Response.ok(true).build();
        }
        return Response.serverError().entity(false).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteService(@PathParam("id") int id) {
        boolean isDeleted = serviceDao.deleteEntity(id);
        if (isDeleted) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("/info")
    public Response getAllServices() {
        List<String> allEntities = new ArrayList<>();
        JsonObject json = new JsonObject();
        for (Service service : serviceDao.getAllEntities()) {
            json.addProperty("client", service.getLogin());
            json.addProperty("password", service.getPassword());
            String token = TokenConverter.getToken(service.getLogin(), service.getPassword());
            json.addProperty("token", token);
            allEntities.add(json.toString());
        }
        return Response.ok(allEntities.toString()).build();
    }
}
