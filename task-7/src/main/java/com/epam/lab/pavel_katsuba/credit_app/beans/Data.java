package com.epam.lab.pavel_katsuba.credit_app.beans;

import java.util.List;

public class Data {
    private List<User> users;
    private List<Credit> credits;
    private List<Discount> discounts;
    private List<Event> events;
    private List<Transaction> transactions;

    public Data() {
    }

    public Data(List<User> users, List<Credit> credits, List<Discount> discounts, List<Event> events, List<Transaction> transactions) {
        this.users = users;
        this.credits = credits;
        this.discounts = discounts;
        this.events = events;
        this.transactions = transactions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Data{" +
                "\n users=" + users +
                "\n credits=" + credits +
                "\n discounts=" + discounts +
                "\n events=" + events +
                "\n transactions=" + transactions +
                '}';
    }
}
