package com.epam.lab.pavel_katsuba.producer_consumer.task_impls;

import com.epam.lab.pavel_katsuba.producer_consumer.Constants;
import com.epam.lab.pavel_katsuba.producer_consumer.interfaces.Task;

public class TaskReturn implements Task {
    public static final String CLASS_NAME = "TaskReturn";

    private int id;

    public TaskReturn(int id) {
        this.id = id;
        System.out.println(CLASS_NAME + Constants.NUMBER + id + Constants.CREATED_BY + Thread.currentThread().getName());
    }

    public String getJob(){
        return Thread.currentThread().getName() + Constants.TASK + id;
    }
}
