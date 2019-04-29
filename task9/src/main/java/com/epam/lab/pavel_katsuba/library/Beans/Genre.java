package com.epam.lab.pavel_katsuba.library.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private static final int DEFAULT_ID = -1;
    private int id;
    private String genreName;
}
