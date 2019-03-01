package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Credit;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Period;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditJsonAdapter implements JsonSerializer<Credit>, JsonDeserializer<Credit> {

    private static final String ID = "id";
    private static final String USER_ID = "userId";
    private static final String DATE = "date";
    private static final String PERIOD = "period";
    private static final String MONEY = "money";
    private static final String RATE = "rate";

    @Override
    public Credit deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int id = object.get(ID).getAsInt();
        int userId = object.get(USER_ID).getAsInt();
        LocalDate date = LocalDate.parse(object.get(DATE).getAsString());
        Period period = Period.valueOf(object.get(PERIOD).getAsString().toUpperCase());
        BigDecimal money = object.get(MONEY).getAsBigDecimal();
        double rate = object.get(RATE).getAsDouble();
        return new Credit(id, userId, date, period, money, rate);
    }

    @Override
    public JsonElement serialize(Credit credit, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, credit.getId());
        object.addProperty(USER_ID, credit.getUserId());
        object.addProperty(DATE, credit.getDate().toString());
        object.addProperty(PERIOD, credit.getPeriod().toString());
        object.addProperty(MONEY, credit.getMoney());
        object.addProperty(RATE, credit.getRate());
        return object;
    }
}
