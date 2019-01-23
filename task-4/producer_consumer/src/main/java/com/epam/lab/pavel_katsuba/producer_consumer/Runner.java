package com.epam.lab.pavel_katsuba.producer_consumer;

import com.epam.lab.pavel_katsuba.producer_consumer.consumers.ConsumerWithTaskPrint;
import com.epam.lab.pavel_katsuba.producer_consumer.consumers.ConsumerWithTaskReturn;
import com.epam.lab.pavel_katsuba.producer_consumer.factories.ConsumerFactory;
import com.epam.lab.pavel_katsuba.producer_consumer.factories.ProducerFactory;
import com.epam.lab.pavel_katsuba.producer_consumer.factories.ThreadFactory;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;
import com.epam.lab.pavel_katsuba.producer_consumer.producers.Producer;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Runner {
    public static void main(String[] args){

        LinkedBlockingQueue<Task> pool = new LinkedBlockingQueue<>();

        System.out.println("insert number of producers");
        Scanner scanner = new Scanner(System.in);
        List<Runnable> producers = ProducerFactory.getProducers(scanner.nextInt(),
                new Producer(pool));

        System.out.println("insert number of consumers for print task");
        scanner = new Scanner(System.in);
        List<Runnable> consumers = ConsumerFactory.getConsumers(scanner.nextInt(),
                new ConsumerWithTaskPrint(pool));

        System.out.println("insert number of consumers for return task");
        scanner = new Scanner(System.in);
        consumers.addAll(ConsumerFactory.getConsumers(scanner.nextInt(),
                new ConsumerWithTaskReturn(pool)));


        List<Thread> producerThreads = ThreadFactory.getThreads(producers);
        List<Thread> consumerThreads = ThreadFactory.getThreads(consumers);
        for (Thread producer : producerThreads){
            producer.start();
        }
        for (Thread consumer : consumerThreads){
            consumer.start();
        }
        for (Thread producer : producerThreads){
            try {
                producer.join();
            } catch (InterruptedException e) {
                System.out.printf(Constants.PRODUCER_EXCEPTION, producer.getName());
            }
        }

        while (!pool.isEmpty());

        for (Thread consumer : consumerThreads){
            consumer.interrupt();
        }

        System.out.println("Finish");

    }
}
