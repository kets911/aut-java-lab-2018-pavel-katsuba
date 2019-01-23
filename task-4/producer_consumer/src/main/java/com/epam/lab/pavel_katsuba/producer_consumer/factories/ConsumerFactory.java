package com.epam.lab.pavel_katsuba.producer_consumer.factories;

import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Consumer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerFactory {
    public static List<Runnable> getConsumers(int number, Consumer consumer){
        List<Runnable> consumers = new ArrayList<>();
        for (; number != 0; number--){
            consumers.add(consumer);
        }
        return consumers;
    }
}
