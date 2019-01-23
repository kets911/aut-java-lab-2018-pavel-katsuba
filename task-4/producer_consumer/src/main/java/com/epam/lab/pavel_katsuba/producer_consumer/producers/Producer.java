package com.epam.lab.pavel_katsuba.producer_consumer.producers;

import com.epam.lab.pavel_katsuba.producer_consumer.Constants;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;
import com.epam.lab.pavel_katsuba.producer_consumer.task_impls.TaskPrint;
import com.epam.lab.pavel_katsuba.producer_consumer.task_impls.TaskReturn;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private LinkedBlockingQueue<Task> pool;
    private static AtomicInteger tasksNum;
    private static AtomicInteger count = new AtomicInteger(1);


    public Producer(LinkedBlockingQueue<Task> pool) {
        this.pool = pool;
        if (tasksNum == null){
            initTasksNumber();
        }
    }

    private void initTasksNumber(){
        System.out.println("insert number of tasks");
        Scanner scanner = new Scanner(System.in);
        tasksNum = new AtomicInteger(scanner.nextInt());
    }

    public void run() {
        try {
            while (tasksNum.getAndDecrement() > 0) {
                if (tasksNum.get() % 2 == 0){
                    pool.put(new TaskReturn(count.getAndIncrement()));
                } else {
                    pool.put(new TaskPrint(count.getAndIncrement()));
                }
            }
        } catch (InterruptedException e){
            System.out.printf(Constants.PRODUCER_EXCEPTION, Thread.currentThread().getName());
        }
    }
}
