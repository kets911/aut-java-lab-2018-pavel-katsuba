package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.interfaces.DiscountPeriod;

import java.time.LocalDate;

public class DiscountPeriodOne implements DiscountPeriod {
    private LocalDate date;

    public DiscountPeriodOne(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean isHit(LocalDate date) {
        return this.date.isEqual(date);
    }
}
