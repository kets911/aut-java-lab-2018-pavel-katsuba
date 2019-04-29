package com.epam.lab.pavel_katsuba.credit_app.dao_impl;

import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.beans.User;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.BankDAO;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;

import java.util.List;

public class UserDAOImpl implements BankDAO<User> {
    private final DBManager<Data> DBManager;

    public UserDAOImpl(DBManager<Data> DBManager) {
        this.DBManager = DBManager;
    }

    @Override
    public void create(User entity) {
        Data data = DBManager.getFromDB();
        List<User> users = data.getUsers();
        entity.setId(users.size());
        users.add(entity);
        DBManager.setToDB(data);
    }

    @Override
    public User read(int id) {
        Data data = DBManager.getFromDB();
        return read(id, data.getUsers());
    }

    @Override
    public List<User> readAll() {
        return DBManager.getFromDB().getUsers();
    }

    @Override
    public User update(int idOldUser, User entity) {
        Data data = DBManager.getFromDB();
        List<User> users = data.getUsers();
        int index = users.indexOf(read(idOldUser, users));
        User oldUser = users.set(index, entity);
        DBManager.setToDB(data);
        return oldUser;

    }

    @Override
    public void delete(int id) {
        Data data = DBManager.getFromDB();
        List<User> users = data.getUsers();
        users.remove(read(id, users));
        DBManager.setToDB(data);
    }
}
