package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.JsonConverter;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Message;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.MessageDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
public class MessageController {
    public static final String MESSAGE_EXIST = "message %s already exist";
    private VneklasnikiDao<Message> messageDao = new MessageDao(MySqlDBManager.instance());
    private static JsonConverter converter = new JsonConverter();

    @GET
    public Response getAllMessages() {
        List<String> messages = new ArrayList<>();
        for (Message message : messageDao.getAllEntities()) {
            messages.add(converter.toJson(message));
        }
        return Response.ok(messages.toString()).build();
    }

    @GET
    @Path("/user/to/{id}")
    public Response getUserMessage(@PathParam("id") int userId) {
        List<String> messages = new ArrayList<>();
        for (Message message : messageDao.getAllEntities()) {
            if (message.getToUserId() == userId) {
                messages.add(converter.toJson(message));
            }
        }
        return Response.ok(messages).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMessage(String json) {
        Message message = converter.fromJson(json, Message.class);
        int messageId = messageDao.getEntityId(message);
        if (messageId == Constants.NAN_ID) {
            messageId = messageDao.create(message);
            return Response.ok(messageId).build();
        }
        return Response.status(400).entity(String.format(MESSAGE_EXIST, message)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response changeMessage(@PathParam("id") int id, String json) {
        Message message = converter.fromJson(json, Message.class);
       return getResponse(messageDao.putEntity(id, message));
    }

    @DELETE
    @Path("{id}")
    public Response deleteMessage(@PathParam("id") int id) {
        return getResponse(messageDao.deleteEntity(id));
    }

    private Response getResponse(boolean isSuccessful) {
        if (isSuccessful) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
