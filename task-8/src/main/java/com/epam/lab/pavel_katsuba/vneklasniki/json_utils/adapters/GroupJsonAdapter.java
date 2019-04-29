package com.epam.lab.pavel_katsuba.vneklasniki.json_utils.adapters;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Group;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class GroupJsonAdapter implements JsonDeserializer<Group>, JsonSerializer<Group> {

    private static final String GROUP_NAME = "groupName";
    private static final String AUTHOR = "author";
    private static final String CREATION_DATE = "creationDate";

    @Override
    public Group deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String groupName = object.get(GROUP_NAME).getAsString();
        String author = object.get(AUTHOR).getAsString();
        LocalDate date = LocalDate.parse(object.get(CREATION_DATE).getAsString());
        return new Group(Constants.NAN_ID, groupName, date, author);
    }

    @Override
    public JsonElement serialize(Group src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", src.getId());
        object.addProperty(GROUP_NAME, src.getName());
        object.addProperty(AUTHOR, src.getAuthor());
        object.addProperty(CREATION_DATE, src.getCreationDate().toString());
        return object;
    }
}
