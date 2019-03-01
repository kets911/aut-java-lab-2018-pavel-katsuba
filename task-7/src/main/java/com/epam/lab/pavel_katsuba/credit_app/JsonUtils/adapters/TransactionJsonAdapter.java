package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Currency;
import com.epam.lab.pavel_katsuba.credit_app.beans.Transaction;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionJsonAdapter implements JsonDeserializer<Transaction>, JsonSerializer<Transaction> {

    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String USER_ID = "userId";
    private static final String CREDIT_ID = "creditId";
    private static final String CURRENCY = "currency";
    private static final String MONEY = "money";

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int id = object.get(ID).getAsInt();
        LocalDate date = LocalDate.parse(object.get(DATE).getAsString());
        int userId = object.get(USER_ID).getAsInt();
        int creditId = object.get(CREDIT_ID).getAsInt();
        Currency currency = Currency.valueOf(object.get(CURRENCY).getAsString().toUpperCase());
        BigDecimal money = object.get(MONEY).getAsBigDecimal();
        return new Transaction(id, date, userId, creditId, currency, money);
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, transaction.getId());
        object.addProperty(DATE, transaction.getDate().toString());
        object.addProperty(USER_ID, transaction.getUserId());
        object.addProperty(CREDIT_ID, transaction.getCreditId());
        object.addProperty(CURRENCY, transaction.getCurrency().toString());
        object.addProperty(MONEY, transaction.getMoney());
        return object;
    }
}
