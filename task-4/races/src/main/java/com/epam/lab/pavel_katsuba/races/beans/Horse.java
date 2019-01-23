package com.epam.lab.pavel_katsuba.races.beans;

import com.epam.lab.pavel_katsuba.races.Constants;

import java.util.concurrent.CountDownLatch;

public class Horse implements Runnable {
    private final CountDownLatch START;
    private String name;
    private int speed;

    public Horse(String name, int speed, CountDownLatch start) {
        this.name = name;
        this.speed = speed;
        START = start;
    }

    private int getDistance(int time){
        return Hippodrom.getDistance() - (speed * time);
    }
    private int getTimeToFinish(){
        return Hippodrom.getDistance() / speed;
    }

    @Override
    public void run() {
        try {
            System.out.println(Constants.HORSE + name + Constants.READY);
            START.countDown();
            START.await();
            System.out.println(Constants.HORSE + name + Constants.STARTED);
            for (int i = 1; getDistance(i) > 0; i++){
                Thread.sleep(1000);
                System.out.println(new StringBuilder(Constants.HORSE)
                        .append(name)
                        .append(Constants.STILL)
                        .append(getDistance(i))
                        .append(Constants.METERS));
            }
            System.out.println(Constants.HORSE + name + Constants.FINISHED);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
