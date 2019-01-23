package com.epam.lab.pavel_katsuba.producer_consumer.factories;

import com.epam.lab.pavel_katsuba.producer_consumer.Constants;

import java.util.ArrayList;
import java.util.List;

public class ThreadFactory {
    public static List<Thread> getThreads(List<Runnable> tasks){
        int i = 1;
        List<Thread> threads = new ArrayList<>();
        for (Runnable task : tasks){
            threads.add(new Thread(task, task.getClass().getSimpleName() + Constants.SPACE + i++));
        }
        return threads;
    }
}
