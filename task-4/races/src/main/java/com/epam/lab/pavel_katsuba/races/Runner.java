package com.epam.lab.pavel_katsuba.races;

import com.epam.lab.pavel_katsuba.races.beans.Hippodrom;
import com.epam.lab.pavel_katsuba.races.factories.HorseFactory;
import com.epam.lab.pavel_katsuba.races.factories.ThreadFactory;

import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args){
        Scanner scanner;
        System.out.println("Insert distance");
        scanner = new Scanner((System.in));
        Hippodrom.setDistance(scanner.nextInt());

        System.out.println("Insert number of horses");
        scanner = new Scanner(System.in);
        List<Runnable> horses = HorseFactory.getHorses(scanner.nextInt());

        for (Thread thread : ThreadFactory.getThreads(horses)){
            thread.start();
        }
    }
}
