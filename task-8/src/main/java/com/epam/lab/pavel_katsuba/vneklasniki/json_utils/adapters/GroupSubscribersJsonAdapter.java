package com.epam.lab.pavel_katsuba.vneklasniki.json_utils.adapters;

import com.epam.lab.pavel_katsuba.vneklasniki.beans.GroupSubscribers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class GroupSubscribersJsonAdapter implements JsonSerializer<GroupSubscribers> {
    private static final String GROUP_NAME = "groupName";
    private static final String AUTHOR = "author";
    private static final String CREATION_DATE = "creationDate";
    private static final String SUBSCRIBERS = "subscribers";


    @Override
    public JsonElement serialize(GroupSubscribers src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", src.getId());
        object.addProperty(GROUP_NAME, src.getName());
        object.addProperty(AUTHOR, src.getAuthor());
        object.addProperty(CREATION_DATE, src.getCreationDate().toString());
        object.add(SUBSCRIBERS, context.serialize(src.getSubscribers()));
        return object;
    }
}
