package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Group {
    private final int id;
    private final String name;
    private final LocalDate creationDate;
    private final String author;
}
