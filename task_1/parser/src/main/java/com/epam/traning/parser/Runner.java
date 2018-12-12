package com.epam.traning.parser;

import com.epam.traning.parser.exceptions.IOExpressionException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        if (args[0] == null || args[1] ==null){
            throw new IllegalArgumentException(Constants.NO_PARAM_EXCEPTION);
        }
        String input = args[0];
        String output = args[1];
        IOFile ioFile = new IOFile();
        Gson gson = new Gson().newBuilder().create();
        try {
            List<String> expressions = ioFile.getExpressions(input);
            for (String expression : expressions){
                try {
                    Person person = gson.fromJson(expression, Person.class);
                    ioFile.writeInFile(output, person.toString());
                }catch (JsonSyntaxException e){
                    ioFile.writeInFile(output, Constants.WRONG_JSON_EXCEPTION);
                }
            }
        }catch (IOExpressionException e){
            ioFile.writeInFile(output, e.getMessage());
        }
    }
}
