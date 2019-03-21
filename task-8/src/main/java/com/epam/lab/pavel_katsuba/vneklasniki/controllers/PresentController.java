package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.JsonConverter;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Present;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.PresentDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/present")
@Produces(MediaType.APPLICATION_JSON)
public class PresentController {
    private static JsonConverter converter = new JsonConverter();
    private VneklasnikiDao<Present> presentDao = new PresentDao(MySqlDBManager.instance());

    @GET
    public Response getAllPresents() {
        List<String> presents = new ArrayList<>();
        for (Present present : presentDao.getAllEntities()) {
            presents.add(converter.toJson(present));
        }
        return Response.ok(presents.toString()).build();
    }

    @GET
    @Path("/{id}")
    public Response getPresent(@PathParam("id") int id) {
        Present entity = presentDao.getEntity(id);
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("FromId", entity.getOwnerId());
        object.addProperty("ToId", entity.getReceiverId());
        object.addProperty("description", entity.getDescription());
        object.addProperty("creationDate", entity.getCreateDate().toString());
        return Response.ok(object.toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPresent(String json) {
        Present present = converter.fromJson(json, Present.class);
        int presentId = presentDao.getEntityId(present);
        if (presentId == Constants.NAN_ID) {
            presentId = presentDao.create(present);
            return Response.ok(presentId).build();
        }
        return Response.status(400).entity(String.format("present %s already exist", present)).build();
    }

    @PUT
    @Path("/{id}")
    public Response changePresent(@PathParam("id") int id, String json) {
        Present present = converter.fromJson(json, Present.class);
        boolean isChanged = presentDao.putEntity(id, present);
        if (isChanged) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @DELETE
    @Path("/{id}")
    public Response changePresent(@PathParam("id") int id) {
        boolean isDeleted = presentDao.deleteEntity(id);
        if (isDeleted) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }
}
