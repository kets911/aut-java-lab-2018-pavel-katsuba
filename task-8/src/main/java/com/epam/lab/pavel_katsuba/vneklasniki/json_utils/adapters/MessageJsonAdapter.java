package com.epam.lab.pavel_katsuba.vneklasniki.json_utils.adapters;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Message;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class MessageJsonAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {
    @Override
    public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int fromUserId = object.get("fromUserId").getAsInt();
        int toUserId = object.get("toUserId").getAsInt();
        LocalDate createDate = LocalDate.parse(object.get("createDate").getAsString());
        String message = object.get("message").getAsString();
        return new Message(Constants.NAN_ID,fromUserId, toUserId, createDate, message);
    }

    @Override
    public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("fromUserId", src.getFromUserId());
        object.addProperty("toUserId", src.getToUserId());
        object.addProperty("createDate", src.getCreateDate().toString());
        object.addProperty("message", src.getMessage());
        return object;
    }
}
