package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.converters;

import com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters.*;
import com.epam.lab.pavel_katsuba.credit_app.beans.*;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class BankDBManager implements DBManager<Data> {
    private File file;
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Data.class, new DataAdapter())
            .registerTypeAdapter(User.class, new UserJsonAdapter())
            .registerTypeAdapter(Credit.class, new CreditJsonAdapter())
            .registerTypeAdapter(Transaction.class, new TransactionJsonAdapter())
            .registerTypeAdapter(Discount.class, new DiscountJsonAdapter())
            .registerTypeAdapter(Event.class, new EventJsonAdapter())
            .setPrettyPrinting()
            .create();

    public BankDBManager(File file) {
        this.file = file;
    }

    @Override
    public Data getFromDB() {
        try (Reader reader = new FileReader(file)) {

            BankPojo pojo = gson.fromJson(reader, BankPojo.class);
            return pojo.getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setToDB(Data data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            BankPojo pojo = new BankPojo();
            pojo.setData(data);
            String json = gson.toJson(data);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
