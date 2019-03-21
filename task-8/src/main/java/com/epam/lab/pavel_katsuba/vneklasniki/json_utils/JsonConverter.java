package com.epam.lab.pavel_katsuba.vneklasniki.json_utils;

import com.epam.lab.pavel_katsuba.vneklasniki.beans.*;
import com.epam.lab.pavel_katsuba.vneklasniki.json_utils.adapters.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Message.class, new MessageJsonAdapter())
            .registerTypeAdapter(Present.class, new PresentJsonAdapter())
            .registerTypeAdapter(Group.class, new GroupJsonAdapter())
            .registerTypeAdapter(GroupSubscribers.class, new GroupSubscribersJsonAdapter())
            .registerTypeAdapter(User.class, new UserJsonAdapter())
            .setPrettyPrinting()
            .create();

    public <T> T fromJson(String json, Class<? extends T> type) {
        return gson.fromJson(json, type);
    }

    public <T> String toJson(T entity) {
        return gson.toJson(entity);
    }
}
