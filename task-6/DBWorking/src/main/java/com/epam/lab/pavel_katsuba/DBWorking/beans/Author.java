package com.epam.lab.pavel_katsuba.DBWorking.beans;

import lombok.Data;

@Data
public class Author {
    private static final int DEFAULT_ID = -1;
    private int id;
    private final String authorName;

    public Author(int id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public Author(String authorName) {
        this.id = DEFAULT_ID;
        this.authorName = authorName;
    }
}
