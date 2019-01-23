package com.epam.lab.pavel_katsuba.races.factories;

import com.epam.lab.pavel_katsuba.races.Constants;
import com.epam.lab.pavel_katsuba.races.beans.Horse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class HorseFactory {
    public static List<Runnable> getHorses(int number){
        final CountDownLatch START = new CountDownLatch(number);
        List<Runnable> horses = new ArrayList<>();
        Scanner scanner;
        for (; number > 0; number--){
            System.out.println(Constants.INSERT_HORSE);
            scanner = new Scanner(System.in);
            String name = scanner.next();
            System.out.println(Constants.INSERT_SPEED);
            scanner = new Scanner(System.in);
            int speed = scanner.nextInt();
            horses.add(new Horse(name, speed, START));
        }
        return horses;
    }
}
