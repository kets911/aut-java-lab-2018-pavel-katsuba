package com.epam.lab.pavel_katsuba.credit_app.interfaces;

public interface DBManager<T> {
    T getFromDB();

    void setToDB(T entity);
}
