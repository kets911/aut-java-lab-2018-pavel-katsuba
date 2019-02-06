package com.epam.lab.pavel_katsuba.producer_consumer;

import com.epam.lab.pavel_katsuba.producer_consumer.consumers.ConsumerWithTaskPrint;
import com.epam.lab.pavel_katsuba.producer_consumer.consumers.ConsumerWithTaskReturn;
import com.epam.lab.pavel_katsuba.producer_consumer.factories.ConsumerFactory;
import com.epam.lab.pavel_katsuba.producer_consumer.factories.ProducerFactory;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;
import com.epam.lab.pavel_katsuba.producer_consumer.producers.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class Runner {

    public static void main(String[] args){
        LinkedBlockingQueue<Task> pool = new LinkedBlockingQueue<>();

        System.out.println("insert number of producers");
        Scanner scanner = new Scanner(System.in);
        int producerNumber = scanner.nextInt();
        List<Runnable> producers = ProducerFactory.getProducers(producerNumber,
                new Producer(pool));

        System.out.println("insert number of consumers for print task");
        scanner = new Scanner(System.in);
        int printConsumerNumber = scanner.nextInt();
        List<Runnable> consumers = ConsumerFactory.getConsumers(printConsumerNumber,
                new ConsumerWithTaskPrint(pool));

        System.out.println("insert number of consumers for return task");
        scanner = new Scanner(System.in);
        int returnConsumerNumber = scanner.nextInt();
        consumers.addAll(ConsumerFactory.getConsumers(returnConsumerNumber,
                new ConsumerWithTaskReturn(pool)));

        ExecutorService consumerService = Executors
                .newFixedThreadPool(printConsumerNumber + returnConsumerNumber);
        ExecutorService producerService = Executors.newFixedThreadPool(producerNumber);

        for (Runnable consumer : consumers){
            consumerService.submit(consumer);
        }

        List<Future> tasks = new ArrayList<>();
        for (Runnable producer : producers){
            tasks.add(producerService.submit(producer));
        }

        for (Future task : tasks) {
            while (!task.isDone());
        }
        producerService.shutdown();

        while (!pool.isEmpty());
        consumerService.shutdownNow();

        System.out.println("Finish");
    }
}
