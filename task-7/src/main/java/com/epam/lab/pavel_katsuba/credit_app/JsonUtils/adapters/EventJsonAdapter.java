package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Event;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Currency;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EventJsonAdapter implements JsonDeserializer<Event>, JsonSerializer<Event> {

    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String CURRENCY = "currency";
    private static final String COST = "cost";

    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int id = object.get(ID).getAsInt();
        LocalDate date = LocalDate.parse(object.get(DATE).getAsString());
        Currency currency = Currency.valueOf(object.get(CURRENCY).getAsString().toUpperCase());
        BigDecimal cost = object.get(COST).getAsBigDecimal();
        return new Event(id, date, currency, cost);
    }

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, src.getId());
        object.addProperty(DATE, src.getDate().toString());
        object.addProperty(CURRENCY, src.getCurrency().toString());
        object.addProperty(COST, src.getCost());
        return object;
    }
}
