package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

@Data
public class ServiceAccessRelate {
    private final Service service;
    private final String access;
    private final int serviceId;
    private final int accessId;
}
