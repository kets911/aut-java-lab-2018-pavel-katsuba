package com.epam.lab.pavel_katsuba.credit_app.beans;

public class SettingPojo {
    private Settings settings;

    public SettingPojo() {
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public SettingPojo(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "SettingPojo{" +
                "settings=" + settings +
                '}';
    }
}
