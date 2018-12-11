package com.epam.traning.memento;

import com.epam.traning.memento.exceptions.CountException;

public class Transaction {

    public Transaction() {
    }
    public static void trans(Count from, Count to , int money){
        Memento<Integer> mementoFrom = from.getMemento();
        Memento<Integer> mementoTo = to.getMemento();
        try {
            from.minusMoney(money);
            to.addMoney(money);
        }catch (CountException e){
            from.restoreState(mementoFrom);
            to.restoreState(mementoTo);
        }
    }
}
