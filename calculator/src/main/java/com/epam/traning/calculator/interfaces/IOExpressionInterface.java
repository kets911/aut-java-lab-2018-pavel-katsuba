package com.epam.traning.calculator.interfaces;

import java.util.List;

public interface IOExpressionInterface {
    List<String> getExpressions(String input);
    void writeInFile(String output, String expressions);
}
