package com.epam.traning.decorator;

import com.epam.traning.decorator.interfaces.Drink;

public class Cofe implements Drink {
    private int cost;

    public Cofe(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int sum() {
        return cost;
    }

    @Override
    public String drinkName() {
        return "cofe ";
    }

    @Override
    public String toString() {
        return "cofe cost: "+ sum();
    }
}
