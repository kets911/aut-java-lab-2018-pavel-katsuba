package com.epam.lab.pavel_katsuba.library.Beans;

import lombok.Value;

@Value
public class Genre {
    private static final int DEFAULT_ID = -1;
    private int id;
    private String genreName;

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public Genre(String genreName) {
        this.id = DEFAULT_ID;
        this.genreName = genreName;
    }
}
