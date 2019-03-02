package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Discount;
import com.epam.lab.pavel_katsuba.credit_app.beans.DiscountPeriodMany;
import com.epam.lab.pavel_katsuba.credit_app.beans.DiscountPeriodOne;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class DiscountJsonAdapter implements JsonSerializer<Discount>, JsonDeserializer<Discount> {

    private static final String TYPE = "type";
    private static final String DATE_FROM = "dateFrom";
    private static final String DATE_TO = "dateTo";
    private static final String DATE = "date";
    private static final String DISCOUNT = "discount";
    private static final String ID = "id";
    private static final String MANY = "MANY";
    private static final String ONE = "ONE";
    private static final String DISCOUNT_PERIOD_MANY = "DiscountPeriodMany";
    private static final String DISCOUNT_PERIOD_ONE = "DiscountPeriodOne";

    @Override
    public Discount deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        Discount discount = new Discount();
        int id = object.get(ID).getAsInt();
        discount.setId(id);
        String discountType = object.get(TYPE).getAsString().toUpperCase();
        if (MANY.equals(discountType)) {
            LocalDate dateFrom = LocalDate.parse(object.get(DATE_FROM).getAsString());
            LocalDate dateTo = LocalDate.parse(object.get(DATE_TO).getAsString());
            discount.setDiscountPeriod(new DiscountPeriodMany(dateFrom, dateTo));
        }
        if (ONE.equals(discountType)) {
            LocalDate date = LocalDate.parse(object.get(DATE).getAsString());
            discount.setDiscountPeriod(new DiscountPeriodOne(date));
        }
        double percentDiscount = object.get(DISCOUNT).getAsDouble();
        discount.setDiscount(percentDiscount);
        return discount;
    }

    @Override
    public JsonElement serialize(Discount discount, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, discount.getId());
        String typeClassName = discount.getDiscountPeriod().getClass().getSimpleName();
        if (typeClassName.equals(DISCOUNT_PERIOD_MANY)) {
            LocalDate dateFrom = ((DiscountPeriodMany) discount.getDiscountPeriod()).getDateFrom();
            LocalDate dateTo = ((DiscountPeriodMany) discount.getDiscountPeriod()).getDateTo();
            object.addProperty(TYPE, MANY);
            object.addProperty(DATE_FROM, dateFrom.toString());
            object.addProperty(DATE_TO, dateTo.toString());
        }
        if (typeClassName.equals(DISCOUNT_PERIOD_ONE)) {
            LocalDate date = ((DiscountPeriodOne) discount.getDiscountPeriod()).getDate();
            object.addProperty(TYPE, ONE);
            object.addProperty(DATE, date.toString());
        }
        object.addProperty(DISCOUNT, discount.getDiscount());
        return object;
    }
}
