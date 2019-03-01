package com.epam.lab.pavel_katsuba.producer_consumer.factories;

import com.epam.lab.pavel_katsuba.producer_consumer.producers.Producer;

import java.util.ArrayList;
import java.util.List;

public class ProducerFactory {
    public static List<Runnable> getProducers(int number, Producer producer){
        List<Runnable> producers = new ArrayList<>();
        for (; number != 0; number--){
            producers.add(producer);
        }
        return producers;
    }
}
