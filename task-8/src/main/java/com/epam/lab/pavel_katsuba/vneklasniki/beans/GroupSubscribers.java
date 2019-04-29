package com.epam.lab.pavel_katsuba.vneklasniki.beans;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GroupSubscribers extends Group {
    private final List<User> subscribers;

    public GroupSubscribers(int id, String name, LocalDate creationDate, String author, List<User> subscribers) {
        super(id, name, creationDate, author);
        this.subscribers = subscribers;
    }
}
