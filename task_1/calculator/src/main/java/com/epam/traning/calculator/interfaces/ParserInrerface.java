package com.epam.traning.calculator.interfaces;

import com.epam.traning.calculator.exceptions.ParserException;

import java.util.List;

public interface ParserInrerface {
    public List<String> parse(String infix) throws ParserException;
}
