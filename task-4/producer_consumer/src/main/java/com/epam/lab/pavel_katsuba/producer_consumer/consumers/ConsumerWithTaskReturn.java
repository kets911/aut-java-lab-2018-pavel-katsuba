package com.epam.lab.pavel_katsuba.producer_consumer.consumers;

import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Consumer;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;
import com.epam.lab.pavel_katsuba.producer_consumer.task_impls.TaskReturn;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumerWithTaskReturn implements Consumer {
    private LinkedBlockingQueue<Task> pool;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String RETURN_IN = " return in ";

    public ConsumerWithTaskReturn(LinkedBlockingQueue<Task> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                LOCK.lock();
                Iterator<Task> iterator = pool.iterator();
                while (iterator.hasNext()){
                    Task task = iterator.next();
                    if (task.getClass().getSimpleName().equals(TaskReturn.CLASS_NAME)) {
                        iterator.remove();
                        System.out.println(new StringBuilder(task.getClass().getSimpleName())
                                .append(RETURN_IN)
                                .append(((TaskReturn) task).getJob()));
                        break;
                    }
                }
            } finally {
                LOCK.unlock();
            }
            Thread.yield();
        }
    }
}
