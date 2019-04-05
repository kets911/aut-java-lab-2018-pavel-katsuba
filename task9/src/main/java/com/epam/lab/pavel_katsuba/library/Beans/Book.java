package com.epam.lab.pavel_katsuba.library.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class Book {
    private int id;
    private final String nameBook;
    private final Date publishingDate;
    private boolean isTaken;
    private List<Author> authors;
    private List<Genre> genres;

    public Book(int id, String nameBook, Date publishingDate, boolean isTaken) {
        this.id = id;
        this.nameBook = nameBook;
        this.publishingDate = publishingDate;
        this.isTaken = isTaken;
    }

    public Book(String nameBook, Date publishingDate, boolean isTaken) {
        this.nameBook = nameBook;
        this.publishingDate = publishingDate;
        this.isTaken = isTaken;
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public List<Genre> getGenres() {
        return new ArrayList<>(genres);
    }

}
