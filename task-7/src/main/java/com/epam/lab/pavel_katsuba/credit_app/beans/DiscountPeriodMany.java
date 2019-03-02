package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.interfaces.DiscountPeriod;

import java.time.LocalDate;

public class DiscountPeriodMany implements DiscountPeriod {
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public DiscountPeriodMany() {
    }

    public DiscountPeriodMany(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    @Override
    public boolean isHit(LocalDate date) {
        return date.isAfter(dateFrom.minusDays(1)) && date.isBefore(dateTo.plusDays(1));
    }

}
