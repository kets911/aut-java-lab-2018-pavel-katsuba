package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.User;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.UserDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.JsonConverter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path(value = "/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private VneklasnikiDao<User> userDao = new UserDao(MySqlDBManager.instance());
    private JsonConverter converter = new JsonConverter();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser( String json) {
        User user = converter.fromJson(json, User.class);
        int userId = userDao.getEntityId(user);
        if (userId == Constants.NAN_ID) {
            userId = userDao.create(user);
            return Response.ok(userId).build();
        }
        return Response.status(400).entity(String.format("user %s already exist", user)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<String> users = new ArrayList<>();
        JsonConverter jsonConverter = new JsonConverter();
        for (User user : userDao.getAllEntities()) {
            users.add(jsonConverter.toJson(user));
        }
        return  Response.ok(users.toString()).build();
    }

    @GET
    @Path(value = "/{id}")
    public Response getUser(@PathParam("id") int id) {
        return Response.ok(converter.toJson(userDao.getEntity(id))).build();
    }

    @PUT
    @Path(value = "{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeUser(@PathParam("id") int id, String json) {
        User user = new JsonConverter().fromJson(json, User.class);
        boolean isChanged = userDao.putEntity(id, user);
        if (isChanged) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @DELETE
    @Path(value = "/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        boolean isDeleted = userDao.deleteEntity(id);
        if (isDeleted) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }
}
