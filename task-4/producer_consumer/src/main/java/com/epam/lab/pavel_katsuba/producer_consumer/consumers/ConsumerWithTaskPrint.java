package com.epam.lab.pavel_katsuba.producer_consumer.consumers;

import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Consumer;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;
import com.epam.lab.pavel_katsuba.producer_consumer.task_impls.TaskPrint;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerWithTaskPrint implements Consumer {
    private LinkedBlockingQueue<Task> pool;
    private static final ReentrantLock lock = new ReentrantLock();

    public ConsumerWithTaskPrint(LinkedBlockingQueue<Task> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                lock.lock();
                Iterator<Task> iterator = pool.iterator();
                while (iterator.hasNext()){
                    Task task = iterator.next();
                    if (task.getClass().getSimpleName().equals(TaskPrint.CLASS_NAME)) {
                        iterator.remove();
                        ((TaskPrint) task).print();
                        break;
                    }
                }
            } finally {
                lock.unlock();
            }
            Thread.yield();
        }
    }
}
