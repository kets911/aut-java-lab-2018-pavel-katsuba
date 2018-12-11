package com.epam.traning.builder;

public class Registration {
    private static final String UNSUCCESS = "account wasn't registered. It have to have name, password and email.";
    private static final String SUCCESS = "Registration successful";
    public Registration() {
    }
    public static void reg(Account account){
        if (account.getName() == null || account.getPassword() == null || account.getEmail() == null){
            System.out.println(UNSUCCESS);
        }else {
            System.out.println(SUCCESS);
        }
    }
}
