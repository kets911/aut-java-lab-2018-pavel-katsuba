package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DataAdapter implements JsonSerializer<Data> {

    private static final String USERS = "users";
    private static final String CREDITS = "credits";
    private static final String DISCOUNTS = "discounts";
    private static final String EVENTS = "events";
    private static final String TRANSACTIONS = "transactions";
    private static final String DATA = "data";

    @Override
    public JsonElement serialize(Data src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add(USERS, context.serialize(src.getUsers()));
        object.add(CREDITS, context.serialize(src.getCredits()));
        object.add(DISCOUNTS, context.serialize(src.getDiscounts()));
        object.add(EVENTS, context.serialize(src.getEvents()));
        object.add(TRANSACTIONS, context.serialize(src.getTransactions()));
        JsonObject data = new JsonObject();
        data.add(DATA, object);
        return data;
    }
}
