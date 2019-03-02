package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Settings;
import com.epam.lab.pavel_katsuba.credit_app.beans.ShowFor;
import com.epam.lab.pavel_katsuba.credit_app.beans.UserMatchById;
import com.epam.lab.pavel_katsuba.credit_app.beans.UserMatchByName;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.ShowType;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.SortType;
import com.epam.lab.pavel_katsuba.credit_app.exceptions.SettingValidateException;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.ShowForMatcher;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingsAdapter implements JsonDeserializer<Settings> {

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
        LocalDate dateFrom = LocalDate.parse(validate(object.get(DATE_FROM), DATE_FROM).getAsString());
        settings.setDateFrom(dateFrom);
        LocalDate dateTo = LocalDate.parse(validate(object.get(DATE_TO), DATE_TO).getAsString());
        settings.setDateTo(dateTo);
        ShowFor showFor = new ShowFor();
        ShowType showType = ShowType.valueOf(validate(
                validate(object.get(SHOW_FOR), SHOW_FOR)
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
        ShowForMatcher showForMatcher = null;
        if (showType == ShowType.ID) {
            List<Integer> users = new ArrayList<>();
            for (JsonElement o : usersAsList) {
                users.add(o.getAsInt());
            }
            showForMatcher = new UserMatchById(users);
        }
        if (showType == ShowType.NAME) {
            List<String> users = new ArrayList<>();
            for (JsonElement o : usersAsList) {
                users.add(o.getAsString());
            }
            showForMatcher = new UserMatchByName(users);
        }
        showFor.setShowForMatcher(showForMatcher);
        settings.setShowFor(showFor);
        SortType sortBy = SortType.valueOf(validate(object.get(SORT_BY), SORT_BY).getAsString());
        settings.setSortBy(sortBy);
        JsonArray useDepartmentsArray = validate(object.get(USE_DEPARTMENTS), USE_DEPARTMENTS).getAsJsonArray();
        List<String> useDepartments = new ArrayList<>();
        for (JsonElement element : useDepartmentsArray) {
            useDepartments.add(element.getAsString());
        }
        settings.setUseDepartments(useDepartments);
        double startCostEUR = validate(object.get(START_COST_EUR), START_COST_EUR).getAsDouble();
        settings.setStartCostEUR(startCostEUR);
        double startCostUSD = validate(object.get(START_COST_USD), START_COST_USD).getAsDouble();
        settings.setStartCostUSD(startCostUSD);
        return settings;
    }

    private <T> T validate(T element, String fieldName) {
        if (element == null) {
            throw new SettingValidateException(fieldName + NOT_INCLUDED_EXCEPTION);
        }
        return element;
    }
}
