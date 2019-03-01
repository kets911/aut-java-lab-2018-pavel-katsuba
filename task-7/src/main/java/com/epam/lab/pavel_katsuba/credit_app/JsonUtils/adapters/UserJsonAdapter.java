package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Gender;
import com.epam.lab.pavel_katsuba.credit_app.beans.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class UserJsonAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SECOND_NAME = "secondName";
    private static final String SEX = "sex";
    private static final String BIRTHDAY = "birthday";

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, user.getId());
        object.addProperty(NAME, user.getName());
        object.addProperty(SECOND_NAME, user.getSecondName());
        object.addProperty(SEX, user.getSex().toString());
        object.addProperty(BIRTHDAY, user.getBirthday().toString());
        return object;
    }

    @Override
    public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int id = object.get(ID).getAsInt();
        String name = object.get(NAME).getAsString();
        String secondName = object.get(SECOND_NAME).getAsString();
        Gender sex = Gender.valueOf(object.get(SEX).getAsString().toUpperCase());
        LocalDate date = LocalDate.parse(object.get(BIRTHDAY).getAsString());
        return new User(id, name, secondName, sex, date);
    }
}
