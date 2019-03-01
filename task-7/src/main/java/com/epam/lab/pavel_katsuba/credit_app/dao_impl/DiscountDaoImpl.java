package com.epam.lab.pavel_katsuba.credit_app.dao_impl;

import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.beans.Discount;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.BankDAO;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;

import java.util.List;

public class DiscountDaoImpl implements BankDAO<Discount> {
    private final DBManager<Data> converter;

    public DiscountDaoImpl(DBManager<Data> converter) {
        this.converter = converter;
    }

    @Override
    public void create(Discount entity) {
        Data data = converter.getFromDB();
        List<Discount> discounts = data.getDiscounts();
        entity.setId(discounts.size());
        discounts.add(entity);
        converter.setToDB(data);
    }

    @Override
    public Discount read(int id) {
        Data data = converter.getFromDB();
        return read(id, data.getDiscounts());
    }

    @Override
    public List<Discount> readAll() {
        Data data = converter.getFromDB();
        return data.getDiscounts();
    }

    @Override
    public Discount update(int id, Discount entity) {
        Data data = converter.getFromDB();
        List<Discount> discounts = data.getDiscounts();
        int index = discounts.indexOf(read(id, discounts));
        Discount oldDiscount = discounts.set(index, entity);
        converter.setToDB(data);
        return oldDiscount;
    }

    @Override
    public void delete(int id) {
        Data data = converter.getFromDB();
        List<Discount> discounts = data.getDiscounts();
        discounts.remove(read(id, discounts));
        converter.setToDB(data);
    }
}
