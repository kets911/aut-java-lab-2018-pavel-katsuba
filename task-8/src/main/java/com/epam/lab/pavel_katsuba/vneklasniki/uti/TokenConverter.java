package com.epam.lab.pavel_katsuba.vneklasniki.uti;

import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TokenConverter {

    public static String getToken(String login, String password) {
        return login + "/" + password + "/" + LocalDateTime.now();
    }

    public static String[] getArrToken(String token) {
        return token.split("/");
    }

//    public static String encryption(Service service) {
//        String encryptLogin = encrypt(service.getLogin());
//        String encryptPass = encrypt(service.getPassword());
//        return encryptLogin + "-" + encryptPass;
//    }
//
//    private static String encrypt(String str) {
//        char[] chars = str.toCharArray();
//        Set<String> charsIndex = new HashSet<>();
//        for (int i = 0; i < chars.length; i++) {
//            charsIndex.add(i + ":" + chars[i]);
//        }
//        return charsIndex.stream().reduce((s, s2) -> s + "/" + s2).get();
//    }
//
//    public static Service decryption(String token) {
//        String[] splitToken = token.split("-");
//        String login = decrypt(splitToken[0]);
//        String password = decrypt(splitToken[1]);
//        return new Service(login, password);
//    }
//
//    private static String decrypt(String str) {
//        String[] keyCharArr = str.split("/");
//        String[] resChars = new String[keyCharArr.length];
//        for (String string : keyCharArr) {
//            String[] split = string.split(":");
//            resChars[Integer.valueOf(split[0])] = split[1];
//        }
//        StringBuilder result = new StringBuilder();
//        for (String string : resChars) {
//            result.append(string);
//        }
//        return result.toString();
//    }
}
