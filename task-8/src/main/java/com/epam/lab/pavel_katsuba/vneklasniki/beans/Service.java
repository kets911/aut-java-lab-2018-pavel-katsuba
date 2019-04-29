package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Service {
    @SerializedName("name")
    private String login;
    private String password;
    private String status;
    private int requestCount;
    private List<String> access;

    public Service(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Service(String login, String password, String status, int requestCount) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.requestCount = requestCount;
    }

    public Service(String login, String password, List<String> access, String status, int requestCount) {
        this.login = login;
        this.password = password;
        this.access = access;
        this.status = status;
        this.requestCount = requestCount;
    }
}
