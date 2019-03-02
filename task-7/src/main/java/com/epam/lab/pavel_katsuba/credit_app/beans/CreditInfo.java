package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.CreditStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditInfo {
    private Credit credit;
    private User user;
    private int transactionCount;
    private BigDecimal debt;
    private CreditStatus status;
    private LocalDate doneDate;

    public CreditInfo() {
    }

    public CreditInfo(Credit credit, User user, int transactionCount, BigDecimal debt, CreditStatus status, LocalDate doneDate) {
        this.credit = credit;
        this.user = user;
        this.transactionCount = transactionCount;
        this.debt = debt;
        this.status = status;
        this.doneDate = doneDate;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public CreditStatus getStatus() {
        return status;
    }

    public void setStatus(CreditStatus status) {
        this.status = status;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public String toString() {
        return "credit id: " + (credit == null ? "null" : credit.getId()) +
                " user id: " + (credit == null ? "null" : credit.getUserId()) +
                " user name: " + user.getName() + " " + user.getSecondName() +
                " transaction count: " + (transactionCount == 0 ? "null" : transactionCount) +
                " debt: " + debt +
                " period: " + (credit == null ? "null" : credit.getPeriod()) +
                " status: " + (status == CreditStatus.DONE ? status + " " + doneDate : status);
    }
}
