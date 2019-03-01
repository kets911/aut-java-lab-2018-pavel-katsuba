package com.epam.lab.pavel_katsuba.races.factories;

import java.util.ArrayList;
import java.util.List;

public class ThreadFactory {
    public static List<Thread> getThreads(List<Runnable> tasks){
        List<Thread> threads = new ArrayList<>();
        for (Runnable task : tasks){
            threads.add(new Thread(task));
        }
        return threads;
    }
}
