package com.epam.lab.pavel_katsuba.credit_app.dao_impl;

import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.beans.Event;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.BankDAO;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;

import java.util.List;

public class EventDAOImpl implements BankDAO<Event> {
    private final DBManager<Data> converter;

    public EventDAOImpl(DBManager<Data> converter) {
        this.converter = converter;
    }

    @Override
    public void create(Event entity) {
        Data data = converter.getFromDB();
        List<Event> events = data.getEvents();
        entity.setId(events.size());
        events.add(entity);
        converter.setToDB(data);
    }

    @Override
    public Event read(int id) {
        Data data = converter.getFromDB();
        return read(id, data.getEvents());
    }

    @Override
    public List<Event> readAll() {
        Data data = converter.getFromDB();
        return data.getEvents();
    }

    @Override
    public Event update(int id, Event entity) {
        Data data = converter.getFromDB();
        List<Event> events = data.getEvents();
        Event oldEvent = read(id, events);
        events.set(events.indexOf(oldEvent), entity);
        converter.setToDB(data);
        return oldEvent;
    }

    @Override
    public void delete(int id) {
        Data data = converter.getFromDB();
        List<Event> events = data.getEvents();
        events.remove(read(id, events));
        converter.setToDB(data);
    }
}
