package com.epam.lab.pavel_katsuba.vneklasniki.factories;

import com.epam.lab.pavel_katsuba.vneklasniki.beans.User;

import java.time.LocalDateTime;

public class TokenFactory {
    public static String createToken(User user, String password) {
        String date = LocalDateTime.now().toString();
        return user.getName() + " " + password + " " + date;
    }

}
