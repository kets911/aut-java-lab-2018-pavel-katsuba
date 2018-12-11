package com.epam.traning.decorator;

import com.epam.traning.decorator.interfaces.Drink;

public class MilkDrink implements Drink {
    private Drink drink;
    private Milk milk;

    public MilkDrink(Drink drink, Milk milk) {
        this.drink = drink;
        this.milk = milk;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Milk getMilk() {
        return milk;
    }

    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    @Override
    public int sum() {
        return drink.sum() + milk.getCost();
    }

    @Override
    public String drinkName() {
        return drink.drinkName() + "with milk ";
    }

    @Override
    public String toString() {
        return drinkName() + "cost: "+ sum();
    }
}
