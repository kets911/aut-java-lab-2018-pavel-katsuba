package com.epam.traning.calculator;


public class Runner {
    public static void main(String[] args){
        if (args[0] == null || args[1]==null){
            throw new IllegalArgumentException(Constants.NO_PARAM_EXCEPTION);
        }
        String inputPath = args[0];
        String outputPath = args[1];
        Controller controller = new Controller(new Parser(), new Calculator(), new IOFile());
        controller.calculate(inputPath, outputPath);
    }
}
