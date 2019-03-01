package com.epam.lab.pavel_katsuba.credit_app.beans;

public class BankPojo {
    private Data data;

    public BankPojo() {
    }

    public BankPojo(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BankPojo{" +
                "\n data=" + data +
                '}';
    }
}
