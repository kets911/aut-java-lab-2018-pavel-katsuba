package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.SortType;

import java.time.LocalDate;
import java.util.List;

public class Settings {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private ShowFor showFor;
    private SortType sortBy;
    private List<String> useDepartments;
    private double startCostEUR;
    private double startCostUSD;

    public Settings() {
    }

    public Settings(LocalDate dateFrom, LocalDate dateTo, ShowFor showFor, SortType sortBy, List<String> useDepartments, int startCostEUR, int startCostUSD) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.showFor = showFor;
        this.sortBy = sortBy;
        this.useDepartments = useDepartments;
        this.startCostEUR = startCostEUR;
        this.startCostUSD = startCostUSD;
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

    public ShowFor getShowFor() {
        return showFor;
    }

    public void setShowFor(ShowFor showFor) {
        this.showFor = showFor;
    }

    public SortType getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortType sortBy) {
        this.sortBy = sortBy;
    }

    public List<String> getUseDepartments() {
        return useDepartments;
    }

    public void setUseDepartments(List<String> useDepartments) {
        this.useDepartments = useDepartments;
    }

    public double getStartCostEUR() {
        return startCostEUR;
    }

    public void setStartCostEUR(double startCostEUR) {
        this.startCostEUR = startCostEUR;
    }

    public double getStartCostUSD() {
        return startCostUSD;
    }

    public void setStartCostUSD(double startCostUSD) {
        this.startCostUSD = startCostUSD;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", showFor=" + showFor +
                ", sortBy=" + sortBy +
                ", useDepartments=" + useDepartments +
                ", startCostEUR=" + startCostEUR +
                ", startCostUSD=" + startCostUSD +
                '}';
    }
}
