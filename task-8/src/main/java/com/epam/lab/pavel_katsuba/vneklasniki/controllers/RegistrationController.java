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
import com.epam.lab.pavel_katsuba.vneklasniki.utils.PasswordGenerator;
import com.epam.lab.pavel_katsuba.vneklasniki.utils.TokenConverter;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationController {
    public static final String PASSWORD = "password";
    public static final String ID = "id";
    public static final String SERVICE_EXIST_MESSAGE = "service %s already exist";
    public static final String LOGIN = "login";
    public static final String STATUS = "status";
    public static final String REQUEST_COUNT = "requestCount";
    public static final String CLIENT = "client";
    private static JsonConverter converter = new JsonConverter();
    private VneklasnikiDao<Service> serviceDao = new ServiceDao(MySqlDBManager.instance());
    private VneklasnikiDao<String> accessDao = new AccessDao(MySqlDBManager.instance());
    private VneklasnikiDao<ServiceAccessRelate> relateDao = new ServiceAccessDao(MySqlDBManager.instance());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registration(String json) {
        Service service = converter.fromJson(json, Service.class);
        String login = service.getLogin();
        String password = PasswordGenerator.generate(login);
        service.setPassword(password);
        service.setStatus(Constants.STANDARD);
        service.setRequestCount(10);
        int serviceId = serviceDao.getEntityId(service);
        if (serviceId > 0) {
            return Response.status(400).entity(String.format(SERVICE_EXIST_MESSAGE, login)).build();
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
        return Response.ok(getAnswer(token, service, serviceId)).build();
    }

    private String getAnswer(String token, Service service, int serviceId) {
        JsonObject object = new JsonObject();
        object.addProperty(Constants.TOKEN, token);
        object.addProperty(PASSWORD, service.getPassword());
        object.addProperty(ID, serviceId);
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

    @POST
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
        if (!service.getPassword().equals(password) || Constants.PREMIUM.equals(service.getStatus())) {
            return Response.status(400).build();
        }
        service.setStatus(Constants.PREMIUM);
        service.setRequestCount(service.getRequestCount() + 90);
        boolean isChanged = serviceDao.putEntity(serviceId, service);
        if (isChanged) {
            return getResponse(service);
        }
        return Response.serverError().entity(false).build();
    }

    private Response getResponse(Service service) {
        JsonObject object = new JsonObject();
        String login = service.getLogin();
        object.addProperty(LOGIN, login);
        String password = service.getPassword();
        object.addProperty(PASSWORD, password);
        object.addProperty(Constants.TOKEN, TokenConverter.getToken(login, password));
        object.addProperty(STATUS, service.getStatus());
        object.addProperty(REQUEST_COUNT, service.getRequestCount());
        return Response.ok(object.toString()).build();
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
            json.addProperty(CLIENT, service.getLogin());
            json.addProperty(PASSWORD, service.getPassword());
            String token = TokenConverter.getToken(service.getLogin(), service.getPassword());
            json.addProperty(Constants.TOKEN, token);
            allEntities.add(json.toString());
        }
        return Response.ok(allEntities.toString()).build();
    }
}
