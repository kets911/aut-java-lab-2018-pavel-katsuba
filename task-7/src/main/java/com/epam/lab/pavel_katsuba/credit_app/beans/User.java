package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Gender;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.Entity;

import java.time.LocalDate;

public class User implements Entity {
    private int id;
    private String name;
    private String secondName;
    private Gender sex;
    private LocalDate birthday;

    public User() {

    }

    public User(int id, String name, String secondName, Gender sex, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.sex = sex;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                '}';
    }
}
