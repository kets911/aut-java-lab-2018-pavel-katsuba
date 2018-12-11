package com.epam.traning.builder;

public class Authorization {
    private static final String UNSUCCESS = "Authorization isn't successful. Name or password is empty.";
    private static final String SUCCESS = "Authorization is successful";
    public Authorization() {
    }
    public static void login(Account account){
        if (account.getName() == null || account.getPassword()==null){
            System.out.println(UNSUCCESS);
        }else {
            System.out.println(SUCCESS);
        }
    }
}
