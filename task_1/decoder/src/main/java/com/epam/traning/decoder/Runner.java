package com.epam.traning.decoder;

import com.epam.traning.decoder.exceptions.IOExpressionException;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        if (args[0] == null || args[1] ==null){
            throw new IllegalArgumentException(Constants.NO_PARAM_EXCEPTION);
        }
        String input = args[0];
        String output = args[1];
        IOFile ioFile = new IOFile();

        try {
            List<String> expressions = ioFile.getExpressions(input);
            for (String expression : expressions){
                try {
                    ioFile.writeInFile(output, Abbreviations.valueOf(expression.toUpperCase()).getFullName());
                }catch (IllegalArgumentException e){
                    ioFile.writeInFile(output, Constants.ABBREVIATION_EXCEPTION);
                }
            }
        }catch (IOExpressionException e){
            ioFile.writeInFile(output, e.getMessage());
        }
    }
}
