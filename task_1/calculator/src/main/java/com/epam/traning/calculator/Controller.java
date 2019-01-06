package com.epam.traning.calculator;

import com.epam.traning.calculator.exceptions.CalculatorException;
import com.epam.traning.calculator.exceptions.IOExpressionException;
import com.epam.traning.calculator.interfaces.CalculatorInterface;
import com.epam.traning.calculator.interfaces.ControllerInterface;
import com.epam.traning.calculator.interfaces.IOExpressionInterface;
import com.epam.traning.calculator.interfaces.ParserInrerface;

import java.util.List;

public class Controller implements ControllerInterface {
    private ParserInrerface parser;
    private CalculatorInterface calculator;
    private IOExpressionInterface ioExpression;

    public Controller(ParserInrerface parser, CalculatorInterface calculator, IOExpressionInterface ioExpression) {
        this.parser = parser;
        this.calculator = calculator;
        this.ioExpression = ioExpression;
    }


    @Override
    public void calculate(String inputName, String outputName) {
        try {
            List<String> expressions = ioExpression.getExpressions(inputName);
            for (String expression : expressions) {
                try {
                    List<String> postfix = parser.parse(expression);
                    double result = calculator.calc(postfix);
                    ioExpression.writeInFile(outputName, expression +Constants.EQUAL_DELIMITER + result);
                }catch (CalculatorException | ArithmeticException e){
                    ioExpression.writeInFile(outputName ,e.toString());
                }
            }
        }catch (IOExpressionException e){
            ioExpression.writeInFile(outputName ,e.toString());
        }
    }

}
