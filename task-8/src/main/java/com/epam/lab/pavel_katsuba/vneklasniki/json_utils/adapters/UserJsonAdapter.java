package com.epam.lab.pavel_katsuba.vneklasniki.json_utils.adapters;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class UserJsonAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTHDAY = "birthday";

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String name = object.get(NAME).getAsString();
        String surname = object.get(SURNAME).getAsString();
        LocalDate birthday = LocalDate.parse(object.get(BIRTHDAY).getAsString());
        return new User(Constants.NAN_ID, name, surname, birthday);
    }

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(NAME, src.getName());
        object.addProperty(SURNAME, src.getSurname());
        object.addProperty(BIRTHDAY, src.getBirthday().toString());
        return object;
    }
}
