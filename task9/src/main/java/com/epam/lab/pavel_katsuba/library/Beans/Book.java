package com.epam.lab.pavel_katsuba.library.Beans;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String nameBook;
    private Date publishingDate;
    private boolean isTaken;
    private int count;
    private List<Author> authors;
    private List<Genre> genres;

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public List<Genre> getGenres() {
        return new ArrayList<>(genres);
    }

}
