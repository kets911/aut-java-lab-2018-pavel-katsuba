package com.epam.lab.pavel_katsuba.credit_app.dao_impl;

import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.beans.Transaction;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.BankDAO;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements BankDAO<Transaction> {
    private final DBManager<Data> converter;

    public TransactionDAOImpl(DBManager<Data> converter) {
        this.converter = converter;
    }

    @Override
    public void create(Transaction entity) {
        Data data = converter.getFromDB();
        List<Transaction> transactions = data.getTransactions();
        entity.setId(transactions.size());
        transactions.add(entity);
        converter.setToDB(data);
    }

    @Override
    public Transaction read(int id) {
        Data data = converter.getFromDB();
        return read(id, data.getTransactions());
    }

    @Override
    public List<Transaction> readAll() {
        Data data = converter.getFromDB();
        return data.getTransactions() == null ? new ArrayList<>() : data.getTransactions();
    }

    @Override
    public Transaction update(int id, Transaction entity) {
        Data data = converter.getFromDB();
        List<Transaction> transactions = data.getTransactions();
        int index = transactions.indexOf(read(id, transactions));
        Transaction oldTransaction = transactions.set(index, entity);
        converter.setToDB(data);
        return oldTransaction;
    }

    @Override
    public void delete(int id) {
        Data data = converter.getFromDB();
        List<Transaction> transactions = data.getTransactions();
        transactions.remove(read(id, transactions));
        converter.setToDB(data);
    }
}
