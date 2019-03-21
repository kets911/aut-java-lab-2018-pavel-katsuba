package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Present {
    private final int ownerId;
    private final int receiverId;
    private final String description;
    private final LocalDate createDate;
}
