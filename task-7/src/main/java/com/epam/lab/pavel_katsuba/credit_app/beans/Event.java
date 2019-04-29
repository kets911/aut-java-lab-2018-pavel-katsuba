package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Currency;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Event implements Entity {
    private int id;
    private LocalDate date;
    private Currency currency;
    private BigDecimal cost;

    public Event() {
    }

    public Event(int id, LocalDate date, Currency currency, BigDecimal cost) {
        this.id = id;
        this.date = date;
        this.currency = currency;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", currency=" + currency +
                ", cost=" + cost +
                '}';
    }
}
