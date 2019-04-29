package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.interfaces.DiscountPeriod;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.Entity;

public class Discount implements Entity {
    private int id;
    private double discount;
    private DiscountPeriod discountPeriod;

    public Discount() {
    }

    public Discount(int id, double discount, DiscountPeriod discountPeriod) {
        this.id = id;
        this.discount = discount;
        this.discountPeriod = discountPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public DiscountPeriod getDiscountPeriod() {
        return discountPeriod;
    }

    public void setDiscountPeriod(DiscountPeriod discountPeriod) {
        this.discountPeriod = discountPeriod;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", date=" + discountPeriod +
                ", discount=" + discount +
                '}';
    }
}