package com.epam.lab.pavel_katsuba.vneklasniki.json_utils.adapters;

import com.epam.lab.pavel_katsuba.vneklasniki.beans.Present;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class PresentJsonAdapter implements JsonDeserializer<Present>, JsonSerializer<Present> {

    private static final String FROM_ID = "fromId";
    private static final String TO_ID = "toId";
    private static final String CREATION_DATE = "creationDate";
    private static final String DESCRIPTION = "description";

    @Override
    public Present deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int fromId = object.get(FROM_ID).getAsInt();
        int toId = object.get(TO_ID).getAsInt();
        LocalDate creationDate = LocalDate.parse(object.get(CREATION_DATE).getAsString());
        String description = object.get(DESCRIPTION).getAsString();
        return new Present(fromId, toId, description, creationDate);

    }

    @Override
    public JsonElement serialize(Present src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(FROM_ID, src.getOwnerId());
        object.addProperty(TO_ID, src.getReceiverId());
        object.addProperty(CREATION_DATE, src.getCreateDate().toString());
        object.addProperty(DESCRIPTION, src.getDescription());
        return object;
    }
}
