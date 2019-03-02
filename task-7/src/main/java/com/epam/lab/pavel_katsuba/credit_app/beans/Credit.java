package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Period;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Credit implements Entity {
    private int id;
    private int userId;
    private LocalDate date;
    private Period period;
    private BigDecimal money;
    private double rate;

    public Credit() {
    }

    public Credit(int id, int userId, LocalDate date, Period period, BigDecimal money, double rate) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.period = period;
        this.money = money;
        setRate(rate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", period=" + period +
                ", money=" + money +
                ", rate=" + rate +
                '}';
    }
}
