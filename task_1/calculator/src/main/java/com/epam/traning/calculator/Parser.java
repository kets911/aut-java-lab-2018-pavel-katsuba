package com.epam.traning.calculator;


import com.epam.traning.calculator.exceptions.ParserException;
import com.epam.traning.calculator.interfaces.ParserInrerface;

import java.util.*;

public class Parser implements ParserInrerface {
    private final String PLUS = "+";
    private final String MINUS = "-";
    private final String MULTIPLY = "*";
    private final String DIVISION = "/";
    private final String UNARY_MINUS = "u-";
    private final String OPEN_BRACKET = "(";
    private final String CLOSE_BRACKET = ")";
    private final String SPACE = " ";
    private String operators = "+-*/";
    private String delimiters = "() " + operators;

    private boolean isDelimiter(String token) {
        if (token.length() != 1){ return false;}
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperator(String token) {
        if (token.equals(UNARY_MINUS)){ return true;}
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private int priority(String token) {
        if (token.equals(OPEN_BRACKET)){ return 1;}
        if (token.equals(PLUS) || token.equals(MINUS)){ return 2;}
        if (token.equals(MULTIPLY) || token.equals(DIVISION)){ return 3;}
        return 4;
    }
    @Override
    public List<String> parse(String infix) throws ParserException {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(token)) {
                throw new ParserException(Constants.EXPRESSION_WRONG_END_EXCEPTION, infix);
            }
            if (isDelimiter(token)) {
                switch (token){
                    case SPACE:
                        continue;
                    case OPEN_BRACKET:
                        stack.push(token);
                        break;
                    case CLOSE_BRACKET:
                        while (!OPEN_BRACKET.equals(stack.peek())) {
                            postfix.add(stack.pop());
                            if (stack.isEmpty()) {
                                throw new ParserException(Constants.CLOSE_BRACKET_EXCEPTION, infix);
                            }
                        }
                        stack.pop();
                        break;
                    case MINUS:
                        if(postfix.size()==0 || OPEN_BRACKET.equals(stack.peek())){
                            stack.push(UNARY_MINUS);
                        }else stack.push(token);
                        break;
                    default:
                        while (!stack.isEmpty() && (priority(token) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }
                        stack.push(token);
                        break;
                }
            }else postfix.add(token);
        }
        while (!stack.isEmpty()) {
            if (!isOperator(stack.peek())){
                throw new ParserException(Constants.OPEN_BRACKET_EXCEPTION, infix);
            }
            postfix.add(stack.pop());
        }
        return postfix;
    }
}
