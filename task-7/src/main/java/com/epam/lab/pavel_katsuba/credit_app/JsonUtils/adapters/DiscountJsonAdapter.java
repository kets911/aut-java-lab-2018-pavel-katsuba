package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.Discount;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.DiscountTypes;
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

    @Override
    public Discount deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        Discount discount = new Discount();
        int id = object.get(ID).getAsInt();
        discount.setId(id);
        DiscountTypes discountType = DiscountTypes.valueOf(object.get(TYPE).getAsString().toUpperCase());
        discount.setType(discountType);
        if (discountType == DiscountTypes.MANY) {
            LocalDate dateFrom = LocalDate.parse(object.get(DATE_FROM).getAsString());
            LocalDate dateTo = LocalDate.parse(object.get(DATE_TO).getAsString());
            discount.setDateFrom(dateFrom);
            discount.setDateTo(dateTo);
        }
        if (discountType == DiscountTypes.ONE) {
            LocalDate date = LocalDate.parse(object.get(DATE).getAsString());
            discount.setDate(date);
        }
        double percentDiscount = object.get(DISCOUNT).getAsDouble();
        discount.setDiscount(percentDiscount);
        return discount;
    }

    @Override
    public JsonElement serialize(Discount discount, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, discount.getId());
        DiscountTypes discountType = discount.getType();
        object.addProperty(TYPE, discountType.toString());
        if (discountType == DiscountTypes.MANY) {
            object.addProperty(DATE_FROM, discount.getDateFrom().toString());
            object.addProperty(DATE_TO, discount.getDateTo().toString());
        }
        if (discountType == DiscountTypes.ONE) {
            object.addProperty(DATE, discount.getDate().toString());
        }
        object.addProperty(DISCOUNT, discount.getDiscount());
        return object;
    }
}
