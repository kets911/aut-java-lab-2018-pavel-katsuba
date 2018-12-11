package com.epam.traning.builder;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }
    public AccountBuilder setName(String name){
        account.setName(name);
        return this;
    }
    public AccountBuilder setPassword(String password){
        account.setPassword(password);
        return this;
    }
    public AccountBuilder setEmail(String email){
        account.setEmail(email);
        return this;
    }
    public AccountBuilder setAddress(String address){
        account.setAddress(address);
        return this;
    }
    public AccountBuilder setPhoto(String photo){
        account.setPhoto(photo);
        return this;
    }
    public AccountBuilder setAge(int age){
        account.setAge(age);
        return this;
    }
    public Account create(){
        return account;
    }
}
