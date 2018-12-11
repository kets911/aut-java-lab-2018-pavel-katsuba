package com.epam.traning.decorator;

public class Sugar {
    private int cost;
    public Sugar() {
    }

    public Sugar(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
