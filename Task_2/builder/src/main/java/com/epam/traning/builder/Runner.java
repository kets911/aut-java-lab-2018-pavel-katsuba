package com.epam.traning.builder;

public class Runner {
    public static void main(String[] args){
        System.out.println("First user:");
        Account firstUser = new AccountBuilder()
                .setName("p")
                .setPassword("1")
                .setEmail("k@v.com")
                .create();
        Registration.reg(firstUser);
        Authorization.login(firstUser);
        System.out.println("\r\nSecond user:");
        Account secondUser = new AccountBuilder()
                .setName("p")
                .setPassword("1")
                .create();
        Registration.reg(secondUser);
        Authorization.login(secondUser);

    }
}
