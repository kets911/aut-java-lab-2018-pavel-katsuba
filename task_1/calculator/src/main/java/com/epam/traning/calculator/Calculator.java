
package com.epam.traning.calculator;

import com.epam.traning.calculator.interfaces.CalculatorInterface;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Calculator implements CalculatorInterface {

    @Override
    public Double calc(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        double b, a;
        for (String x : postfix) {
            switch (x) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                        b = stack.pop();
                        a = stack.pop();
                        stack.push(a - b);
                        break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                        b = stack.pop();
                        a = stack.pop();
                        if (b ==0){
                            throw new ArithmeticException("Wrong expression: division by zero");
                        }
                        stack.push(a / b);
                        break;
                case "u-":
                    stack.push(-stack.pop());
                    break;
                default:
                    stack.push(Double.valueOf(x));
                    break;
            }
        }
        return stack.pop();
    }
}
