package com.epam.traning.memento;

public class Runner {
    public static void main(String[] args){
        Count from = new Count(20);
        Count to = new Count(5);
        System.out.println("Success transaction");
        printCounts(from, to);
        Transaction.trans(from, to, 5);
        printCounts(from, to);
        System.out.println("Unsuccess transaction");
        printCounts(from, to);
        Transaction.trans(from, to, 25);
        printCounts(from, to);
    }

    private static void printCounts(Count from, Count to) {
        System.out.println("Count from " + from);
        System.out.println("Count to " + to + "\r\n");
    }
}
