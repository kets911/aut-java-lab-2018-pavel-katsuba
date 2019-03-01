package com.epam.lab.pavel_katsuba.producer_consumer.task_impls;

import com.epam.lab.pavel_katsuba.producer_consumer.Constants;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;

public class TaskPrint implements Task {
    public static final String CLASS_NAME = "TaskPrint";

    private static final String PRINT_IN = " print in ";
    private int id;

    public TaskPrint(int id) {
        this.id = id;
        System.out.println(CLASS_NAME + Constants.NUMBER + id + Constants.CREATED_BY + Thread.currentThread().getName());
    }

    public void print(){
        System.out.println(getClass().getSimpleName() + PRINT_IN + Thread.currentThread().getName() + Constants.TASK + id);
    }
}
