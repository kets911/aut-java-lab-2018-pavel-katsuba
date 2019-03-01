package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Settings;
import com.epam.lab.pavel_katsuba.credit_app.beans.ShowFor;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.ShowType;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.SortType;
import com.epam.lab.pavel_katsuba.credit_app.exceptions.SettingValidateException;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingsAdapter implements JsonSerializer<Settings>, JsonDeserializer<Settings> {

    private static final String DATE_FROM = "dateFrom";
    private static final String DATE_TO = "dateTo";
    private static final String SHOW_FOR = "showFor";
    private static final String TYPE = "type";
    private static final String USERS = "users";
    private static final String SORT_BY = "sortBy";
    private static final String USE_DEPARTMENTS = "useDepartments";
    private static final String START_COST_EUR = "startCostEUR";
    private static final String START_COST_USD = "startCostUSD";
    private static final String NOT_INCLUDED_EXCEPTION = " is not included in settings";

    @Override
    public Settings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Settings settings = new Settings();
        JsonObject object = json.getAsJsonObject();
        LocalDate dateFrom = LocalDate.parse(val(object.get(DATE_FROM), DATE_FROM).getAsString());
        settings.setDateFrom(dateFrom);
        LocalDate dateTo = LocalDate.parse(val(object.get(DATE_TO), DATE_TO).getAsString());
        settings.setDateTo(dateTo);
        ShowFor showFor = new ShowFor();
        ShowType showType = ShowType.valueOf(val(
                val(object.get(SHOW_FOR), SHOW_FOR)
                        .getAsJsonObject().get(TYPE), TYPE)
                .getAsString().toUpperCase());
        showFor.setType(showType);
        List<JsonElement> usersAsList = Stream.of(object.get(SHOW_FOR))
                .peek(jsonElement -> validate(jsonElement, SHOW_FOR))
                .flatMap(jsonElementShowFor -> Stream.of(jsonElementShowFor.getAsJsonObject().get(USERS)))
                .peek(jsonElementUsers -> validate(jsonElementUsers, USERS))
                .flatMap(jsonElement -> {
                    List<JsonElement> elements = new ArrayList<>();
                    for (JsonElement element : jsonElement.getAsJsonArray()) {
                        elements.add(element);
                    }
                    return elements.stream();
                })
                .collect(Collectors.toList());
        if (showType == ShowType.ID) {
            List<Integer> users = new ArrayList<>();
            for (JsonElement o : usersAsList) {
                users.add(o.getAsInt());
            }
            showFor.setIds(users);
        }
        if (showType == ShowType.NAME) {
            List<String> users = new ArrayList<>();
            for (JsonElement o : usersAsList) {
                users.add(o.getAsString());
            }
            showFor.setNames(users);
        }
        settings.setShowFor(showFor);
        SortType sortBy = SortType.valueOf(val(object.get(SORT_BY), SORT_BY).getAsString());
        settings.setSortBy(sortBy);
        JsonArray useDepartmentsArray = val(object.get(USE_DEPARTMENTS), USE_DEPARTMENTS).getAsJsonArray();
        List<String> useDepartments = new ArrayList<>();
        for (JsonElement element : useDepartmentsArray) {
            useDepartments.add(element.getAsString());
        }
        settings.setUseDepartments(useDepartments);
        double startCostEUR = val(object.get(START_COST_EUR), START_COST_EUR).getAsDouble();
        settings.setStartCostEUR(startCostEUR);
        double startCostUSD = val(object.get(START_COST_USD), START_COST_USD).getAsDouble();
        settings.setStartCostUSD(startCostUSD);
        return settings;
    }

    private <T> void validate(T element, String fieldName) {
        if (element == null) {
            throw new SettingValidateException(fieldName + NOT_INCLUDED_EXCEPTION);
        }
    }

    private <T> T val(T element, String fieldName) {
        if (element == null) {
            throw new SettingValidateException(fieldName + NOT_INCLUDED_EXCEPTION);
        }
        return element;
    }

    @Override
    public JsonElement serialize(Settings src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(DATE_FROM, src.getDateFrom().toString());
        object.addProperty(DATE_TO, src.getDateTo().toString());
        JsonObject showForObject = new JsonObject();
        ShowType type = src.getShowFor().getType();
        showForObject.addProperty(TYPE, type.toString());
        JsonArray users = new JsonArray();
        if (type == ShowType.ID) {
            List<Integer> listUsers = src.getShowFor().getIds();
            for (Integer userId : listUsers) {
                users.add(userId);
            }
        }
        if (type == ShowType.NAME) {
            List<String> listUsers = src.getShowFor().getNames();
            for (String userName : listUsers) {
                users.add(userName);
            }
        }
        showForObject.add(USERS, users);
        object.add(SHOW_FOR, showForObject);
        object.addProperty(SORT_BY, src.getSortBy().toString());
        JsonArray useDepartments = new JsonArray();
        for (String department : src.getUseDepartments()) {
            useDepartments.add(department);
        }
        object.add(USE_DEPARTMENTS, useDepartments);
        object.addProperty(START_COST_EUR, src.getStartCostEUR());
        object.addProperty(START_COST_USD, src.getStartCostUSD());
        return object;
    }
}
