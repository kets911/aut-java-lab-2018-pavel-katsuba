package com.epam.lab.pavel_katsuba.credit_app.dao_impl;

import com.epam.lab.pavel_katsuba.credit_app.beans.Credit;
import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.exceptions.DAOException;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.BankDAO;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;

import java.util.List;

public class CreditDAOImpl implements BankDAO<Credit> {
    private final DBManager<Data> converter;

    public CreditDAOImpl(DBManager<Data> converter) {
        this.converter = converter;
    }

    @Override
    public void create(Credit entity) {
        Data data = converter.getFromDB();
        List<Credit> credits = data.getCredits();
        entity.setId(credits.size());
        credits.add(entity);
        converter.setToDB(data);
    }

    @Override
    public Credit read(int id) {
        Data data = converter.getFromDB();
        return read(id, data.getCredits());
    }

    @Override
    public List<Credit> readAll() {
        Data data = converter.getFromDB();
        return data.getCredits();
    }

    @Override
    public Credit update(int id, Credit entity) {
        Data data = converter.getFromDB();
        List<Credit> credits = data.getCredits();
        int index = credits.indexOf(read(id, credits));
        Credit oldCredit = credits.set(index, entity);
        converter.setToDB(data);
        return oldCredit;
    }

    @Override
    public void delete(int id) {
        Data data = converter.getFromDB();
        List<Credit> credits = data.getCredits();
        credits.remove(read(id, credits));
        converter.setToDB(data);
    }
}
