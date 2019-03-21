package com.epam.lab.pavel_katsuba.vneklasniki.uti;

import java.util.Random;

public class PasswordGenerator {
    public static String generate(String login) {
        StringBuilder password = new StringBuilder();
        char[] loginChars = login.toCharArray();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int index = random.nextInt(login.length());
            password.append(loginChars[index]);
        }
        return password.toString();
    }
}
