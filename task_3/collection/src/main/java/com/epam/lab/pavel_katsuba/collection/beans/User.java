package com.epam.lab.pavel_katsuba.collection.beans;

public class User implements Comparable<User> {
    private final String name;
    private final String email;
    private int age;
    private final Sex sex;

    public User(String name, String email, Sex sex) {
        this.name = name;
        this.email = email;
        this.sex = sex;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj instanceof User){
            User that = (User) obj;
            return this.getName().equals(that.getName()) && this.getEmail().equals(that.getEmail())
                    && this.getSex().equals(that.getSex());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = hash * 31 + name.hashCode();
        hash = hash * 31 + email.hashCode();
        hash = hash * 31 + sex.hashCode();
        return hash;
    }

    @Override
    public int compareTo(User that) {
        if (this.equals(that)){
            return 0;
        } else {
            return this.getName().compareTo(that.getName());
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + " age: " + age + " sex: " + sex + " email: " + email + ";";
    }
}
