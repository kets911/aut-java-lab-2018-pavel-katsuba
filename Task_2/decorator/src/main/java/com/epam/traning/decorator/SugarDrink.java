package com.epam.traning.decorator;

import com.epam.traning.decorator.interfaces.Drink;

public class SugarDrink implements Drink {
    private Sugar sugar;
    private Drink drink;
    public SugarDrink() {
    }

    public SugarDrink(Drink drink, Sugar sugar) {
        this.sugar = sugar;
        this.drink = drink;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Sugar getSugar() {
        return sugar;
    }

    public void setSugar(Sugar sugar) {
        this.sugar = sugar;
    }

    @Override
    public int sum() {
        return drink.sum() + sugar.getCost();
    }

    @Override
    public String drinkName() {
        return drink.drinkName() + "with sugar ";
    }

    @Override
    public String toString() {
        return drinkName() + "cost: "+ sum();
    }
}
