package com.epam.traning.decorator;

public class Milk {
    private int cost;
    public Milk() {
    }

    public Milk(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
