package com.epam.traning.decorator;

import com.epam.traning.decorator.interfaces.Drink;

public class Tea implements Drink {
    private int cost;

    public Tea() {
    }

    public Tea(int cost) {
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
        return "tea ";
    }

    @Override
    public String toString() {
        return "tea cost: "+ sum();
    }
}
