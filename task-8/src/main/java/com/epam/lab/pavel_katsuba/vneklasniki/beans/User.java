package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private final int id;
    private final String name;
    private final String surname;
    private final LocalDate birthday;

    public User(int id, String name, String surname, String birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = LocalDate.parse(birthday);
    }

    public User(int id, String name, String surname, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
}
