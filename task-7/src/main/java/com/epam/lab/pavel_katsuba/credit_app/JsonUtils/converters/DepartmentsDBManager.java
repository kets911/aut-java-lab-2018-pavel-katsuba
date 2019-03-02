package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.converters;

import com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters.TransactionJsonAdapter;
import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.beans.Transaction;
import com.epam.lab.pavel_katsuba.credit_app.exceptions.DBManagerException;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class DepartmentsDBManager implements DBManager<Data> {
    private File file;
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Transaction.class, new TransactionJsonAdapter())
            .setPrettyPrinting()
            .create();

    public DepartmentsDBManager(File file) {
        this.file = file;
    }

    @Override
    public Data getFromDB() {
        try (Reader reader = new FileReader(file)) {
            Data data = gson.fromJson(reader, Data.class);
            return data;
        } catch (IOException e) {
            throw new DBManagerException(e);
        }
    }

    @Override
    public void setToDB(Data data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String json = gson.toJson(data);
            writer.write(json);
        } catch (IOException e) {
            throw new DBManagerException(e);
        }
    }

}
