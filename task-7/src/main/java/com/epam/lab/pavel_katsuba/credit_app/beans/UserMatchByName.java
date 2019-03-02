package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.interfaces.ShowForMatcher;

import java.util.List;

public class UserMatchByName implements ShowForMatcher {
    private List<String> users;

    public UserMatchByName() {
    }

    public UserMatchByName(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public boolean isMatch(User user) {
        return users.contains(user.getName());
    }
}
