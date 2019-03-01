package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.DiscountTypes;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.Entity;

import java.time.LocalDate;

public class Discount implements Entity {
    private int id;
    private DiscountTypes type;
    private LocalDate date;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double discount;

    public Discount() {
    }

    public Discount(int id, DiscountTypes type, LocalDate date, LocalDate dateFrom, LocalDate dateTo, double discount) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiscountTypes getType() {
        return type;
    }

    public void setType(DiscountTypes type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", discount=" + discount +
                '}';
    }
}