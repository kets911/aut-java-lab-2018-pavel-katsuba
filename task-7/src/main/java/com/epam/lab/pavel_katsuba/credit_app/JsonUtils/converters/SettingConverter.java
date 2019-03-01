package com.epam.lab.pavel_katsuba.credit_app.JsonUtils.converters;

import com.epam.lab.pavel_katsuba.credit_app.JsonUtils.adapters.SettingsAdapter;
import com.epam.lab.pavel_katsuba.credit_app.beans.SettingPojo;
import com.epam.lab.pavel_katsuba.credit_app.beans.Settings;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class SettingConverter implements DBManager<Settings> {
    private final File file;
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Settings.class, new SettingsAdapter())
            .setPrettyPrinting()
            .create();

    public SettingConverter(File file) {
        this.file = file;
    }

    @Override
    public Settings getFromDB() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(reader, SettingPojo.class).getSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setToDB(Settings entity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String json = gson.toJson(entity);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
