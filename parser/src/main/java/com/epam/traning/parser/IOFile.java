package com.epam.traning.parser;

import com.epam.traning.parser.exceptions.IOExpressionException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {

    public List<String> getExpressions(String inputPath) {
        File inputFile = new File(inputPath);
        if(!inputFile.exists()){
            throw new IOExpressionException(Constants.NO_FILE_EXCEPTION);
        }
        List<String> expressions = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            while(line != null){
                expressions.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expressions;
    }


    public void writeInFile(String outputPath, String stringsForOutput) {
        File outputFile = new File(outputPath);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write(stringsForOutput);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}