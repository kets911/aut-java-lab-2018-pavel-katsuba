package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.JsonConverter;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Friend;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.FriendDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("/friend")
@Produces(MediaType.APPLICATION_JSON)
public class FriendController {
    private static JsonConverter converter = new JsonConverter();
    private VneklasnikiDao<Friend> friendDao = new FriendDao(MySqlDBManager.instance());

    @GET
    public Response getAllFriendRelates() {
        List<String> allEntities = new ArrayList<>();
        for (Friend friend : friendDao.getAllEntities()) {
            allEntities.add(converter.toJson(friend));
        }
        return Response.ok(allEntities.toString()).build();
    }

    @GET
    @Path("/{selfUserId}/{friendUserId}")
    public Response getRelate(@PathParam("selfUserId") int selfUserId, @PathParam("friendUserId") int friendUserId) {
        int two = (selfUserId + 31) * (friendUserId + 31);
        for (Friend friend : friendDao.getAllEntities()) {
            int one = (friend.getUserId() + 31) * (friend.getFriendId() + 31);
            if (one == two) {
                return Response.ok(true).build();
            }
        }
        return Response.ok(false).build();
    }

    @POST
    @Path("/{selfUserId}/{friendUserId}")
    public Response createRelate(@PathParam("selfUserId") int selfUserId, @PathParam("friendUserId") int friendUserId) {
        Friend friend = new Friend(selfUserId, friendUserId, null, null, LocalDate.now());
        int firstId = friendDao.getEntityId(friend);
        if (firstId < 0){
            firstId = friendDao.create(friend);
        }
        friend = new Friend(friendUserId, selfUserId, null, null, LocalDate.now());
        int secondId = friendDao.getEntityId(friend);
        if (secondId < 0){
            try {
                friendDao.create(friend);
                return Response.ok().build();
            } catch (DataBaseException e) {
                boolean isDeleted = friendDao.deleteEntity(firstId);
                if (isDeleted) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                String exceptMessage = String.format("Only one relate was added -> %d to %d with id: %d", selfUserId, friendUserId, firstId);
                return Response.serverError().entity(exceptMessage).build();
            }
        }
        return Response.status(400).entity(String.format("friends with id's: %d, %d already exist", selfUserId, friendUserId)).build();
    }

    @PUT
    @Path("/{selfUserId}/{friendUserId}")
    public Response changeRelate(@PathParam("selfUserId") int selfUserId, @PathParam("friendUserId") int friendUserId) {
        Friend friend = new Friend(selfUserId, friendUserId, null, null, LocalDate.now());
        int friendId = friendDao.getEntityId(friend);
        boolean isChanged = friendDao.putEntity(friendId, friend);
        if (isChanged) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @DELETE
    @Path("/{selfUserId}/{friendUserId}")
    public Response deleteRelate(@PathParam("selfUserId") int selfUserId, @PathParam("friendUserId") int friendUserId) {
        Friend friend = new Friend(selfUserId, friendUserId, null, null, LocalDate.now());
        int friendId = friendDao.getEntityId(friend);
        boolean isDeleted = friendDao.deleteEntity(friendId);
        if (isDeleted) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }
}
