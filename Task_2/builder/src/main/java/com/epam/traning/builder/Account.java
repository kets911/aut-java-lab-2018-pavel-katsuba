package com.epam.traning.builder;

public class Account {
    private String name;
    private String password;
    private String email;
    private String address;
    private int age;
    private String photo;

    public Account() {
    }

    public Account(String name, String password, String email, String address, int age, String photo) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.age = age;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
