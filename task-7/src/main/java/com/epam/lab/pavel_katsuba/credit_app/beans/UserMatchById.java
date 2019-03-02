package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.interfaces.ShowForMatcher;

import java.util.List;

public class UserMatchById implements ShowForMatcher {
    private List<Integer> users;

    public UserMatchById() {
    }

    public UserMatchById(List<Integer> users) {
        this.users = users;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    @Override
    public boolean isMatch(User user) {
        return users.contains(user.getId());
    }
}
