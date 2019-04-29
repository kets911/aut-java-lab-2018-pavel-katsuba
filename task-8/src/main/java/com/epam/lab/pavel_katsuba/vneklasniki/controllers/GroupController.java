package com.epam.lab.pavel_katsuba.vneklasniki.controllers;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.GroupSubscribers;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.GroupUserRelate;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.User;
import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.JsonConverter;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Group;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.GroupDao;
import com.epam.lab.pavel_katsuba.vneklasniki.dao_impl.UserGroupDao;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.MySqlDBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
public class GroupController {
    public static final String GROUP_EXIST = "group %s already exist";
    public static final String RELATE_EXIST = "relate %s already exist";
    private VneklasnikiDao<Group> groupDao = new GroupDao(MySqlDBManager.instance());
    private VneklasnikiDao<GroupUserRelate> userGroupDao = new UserGroupDao(MySqlDBManager.instance());
    private static JsonConverter converter = new JsonConverter();

    @GET
    public Response getGroups() {
        List<String> groups = new ArrayList<>();
        for (Group group : groupDao.getAllEntities()) {
            groups.add(converter.toJson(group));
        }
        return Response.ok(groups.toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGroup(String json) {
        Group group = converter.fromJson(json, Group.class);
        int groupId = groupDao.getEntityId(group);
        if (groupId == Constants.NAN_ID) {
            groupId = groupDao.create(group);
            return Response.ok(groupId).build();
        }
        return Response.status(400).entity(String.format(GROUP_EXIST, group)).build();
    }

    @POST
    @Path("/user/{id_group}/{id_user}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserToGroup(@PathParam("id_group") int id_group, @PathParam("id_user") int id_user) {
        GroupUserRelate entity = new GroupUserRelate(id_user, id_group, null, null, LocalDate.now());
        int relateId = userGroupDao.getEntityId(entity);
        if (relateId == Constants.NAN_ID) {
            relateId = userGroupDao.create(entity);
            return Response.ok(relateId).build();
        }
        return Response.status(400).entity(String.format(RELATE_EXIST, entity)).build();
    }

    @GET
    @Path("/{id}")
    public Response getGroup(@PathParam("id") int id) {
        Group group = groupDao.getEntity(id);
        return Response.ok(converter.toJson(group)).build();
    }

    @GET
    @Path("/user")
    public Response getGroupsWithSubscribers() {
        Map<Integer, GroupSubscribers> subscribersMap = new HashMap<>();
        for (GroupUserRelate groupUserRelate : userGroupDao.getAllEntities()) {
            User user = groupUserRelate.getUser();
            int groupId = groupUserRelate.getGroupId();
            if (subscribersMap.containsKey(groupId)) {
                subscribersMap.get(groupId).getSubscribers().add(user);
            } else {
                List<User> subscribers = new ArrayList<>();
                subscribers.add(user);
                Group group = groupUserRelate.getGroup();
                subscribersMap.put(groupId, new GroupSubscribers(groupId, group.getName(), group.getCreationDate()
                        , group.getAuthor(), subscribers));
            }
        }
        List<String> results = new ArrayList<>();
        for (GroupSubscribers groupSubscribers : subscribersMap.values()) {
            results.add(converter.toJson(groupSubscribers));
        }
        return Response.ok(results.toString()).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getGroupWithSubscribers(@PathParam("id") int id) {
        Map<Integer, GroupSubscribers> subscribersMap = new HashMap<>();
        for (GroupUserRelate groupUserRelate : userGroupDao.getAllEntities()) {
            User user = groupUserRelate.getUser();
            int groupId = groupUserRelate.getGroupId();
            if (subscribersMap.containsKey(groupId)) {
                subscribersMap.get(groupId).getSubscribers().add(user);
            } else {
                List<User> subscribers = new ArrayList<>();
                subscribers.add(user);
                Group group = groupUserRelate.getGroup();
                subscribersMap.put(groupId, new GroupSubscribers(groupId, group.getName(), group.getCreationDate()
                        , group.getAuthor(), subscribers));
            }
        }
        return Response.ok(converter.toJson(subscribersMap.get(id))).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeGroup(@PathParam("id") int id, String json) {
        Group group = converter.fromJson(json, Group.class);
        boolean isChanged = groupDao.putEntity(id, group);
        if (isChanged) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user/{id_group}/{id_user}")
    public Response changeSubscribeDate(@PathParam("id_group") int id_group, @PathParam("id_user") int id_user) {
        GroupUserRelate groupUserRelate = new GroupUserRelate(id_user, id_group, null, null, LocalDate.now());
        int relateId = userGroupDao.getEntityId(groupUserRelate);
        boolean isChanged = userGroupDao.putEntity(relateId, groupUserRelate);
        if (isChanged) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGroup(@PathParam("id") int id) {
        boolean isDeleted = groupDao.deleteEntity(id);
        if (isDeleted) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @DELETE
    @Path("/user/{id_group}/{id_user}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteRelate(@PathParam("id_group") int id_group, @PathParam("id_user") int id_user) {
        GroupUserRelate groupUserRelate = new GroupUserRelate(id_user, id_group, null, null, null);
        int relateId = userGroupDao.getEntityId(groupUserRelate);
        boolean isDeleted = userGroupDao.deleteEntity(relateId);
        if (isDeleted) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }
}
