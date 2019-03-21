package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GroupUserRelate {
    private final int userId;
    private final int groupId;
    private final User user;
    private final Group group;
    private final LocalDate subscribeDate;
}
