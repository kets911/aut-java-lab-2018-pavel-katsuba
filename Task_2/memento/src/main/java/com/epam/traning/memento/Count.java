package com.epam.traning.memento;

import com.epam.traning.memento.exceptions.CountException;

public class Count {
    private int money;

    public Count(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }
    public void minusMoney(int money) {
        if (this.money - money <0){
            throw new CountException("Count can't be less then zero");
        }
        this.money -= money;
    }
    public void restoreState(Memento<Integer> memento){
        this.money = memento.getSavedState();
    }
    public Memento getMemento(){
        return new Memento<>(money);
    }

    @Override
    public String toString() {
        return "money=" + money;
    }
}
