package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Friend {
    private final int userId;
    private final int friendId;
    private final User user;
    private final User friend;
    private final LocalDate createDate;
}
