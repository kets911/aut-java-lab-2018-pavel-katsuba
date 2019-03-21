package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Message {
    private final int id;
    private final int fromUserId;
    private final int toUserId;
    private final LocalDate createDate;
    private final String message;
}
