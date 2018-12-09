package com.epam.traning.coins;


import com.epam.traning.coins.exceptions.ExpressionCoinsException;
import com.epam.traning.coins.exceptions.GroupException;
import com.epam.traning.coins.exceptions.IOExpressionException;
import com.epam.traning.coins.exceptions.ParserExceptions;

import java.util.Comparator;
import java.util.List;

public class Runner {
    public static void main(String[] args){
        if (args[0] == null || args[1]==null){
            throw new IllegalArgumentException(Constants.NO_PARAM_EXCEPTION);
        }
        String input = args[0];
        String output = args[1];
        IOFile ioFile = new IOFile();
        Parser parser = new Parser();
        Splitter splitter = new Splitter();
        try {
            List<String> lines = ioFile.getExpressions(input);
            for (String line : lines) {
                try {
                    ExpressionCoins expression = parser.parse(line);
                    List<String> results = splitter.split(expression);
                    results.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o2.length() - o1.length();
                        }
                    });
                    for (String result : results) {
                        ioFile.writeInFile(output, result);
                    }
                }catch (GroupException | ParserExceptions | ExpressionCoinsException e){
                    ioFile.writeInFile(output, e.getMessage());
                }
                ioFile.writeInFile(output, Constants.EXPRESSION_DELIMITER);
            }
        }catch (IOExpressionException e){
            ioFile.writeInFile(output, e.getMessage());
        }

    }
}