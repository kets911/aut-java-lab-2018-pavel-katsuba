package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.ShowType;

import java.util.List;

public class ShowFor {
    private ShowType type;
    private List<Integer> ids;
    private List<String> names;

    public ShowFor() {
    }

    public ShowFor(ShowType type, List<Integer> ids, List<String> names) {
        this.type = type;
        this.ids = ids;
        this.names = names;
    }

    public ShowType getType() {
        return type;
    }

    public void setType(ShowType type) {
        this.type = type;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "ShowFor{" +
                "type=" + type +
                ", ids=" + ids +
                ", names=" + names +
                '}';
    }
}
