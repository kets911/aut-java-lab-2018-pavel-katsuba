package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Currency;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction implements Entity {
    private int id;
    private LocalDate date;
    private int userId;
    private int creditId;
    private Currency currency;
    private BigDecimal money;

    public Transaction() {
    }

    public Transaction(int id, LocalDate date, int userId, int creditId, Currency currency, BigDecimal money) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.creditId = creditId;
        this.currency = currency;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", creditId=" + creditId +
                ", currency=" + currency +
                ", money=" + money +
                '}';
    }
}
