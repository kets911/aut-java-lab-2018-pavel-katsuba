package com.epam.traning.memento;

public class Memento<T> {
    private final T State;

    public Memento(T State) {
        this.State = State;
    }

    public T getSavedState() {
        return State;
    }
}
