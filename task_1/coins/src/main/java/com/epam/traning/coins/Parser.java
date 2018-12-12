package com.epam.traning.coins;

import com.epam.traning.coins.exceptions.ParserExceptions;


public class Parser {

    public ExpressionCoins parse(String lines){
        try{
            String[] mLine = lines.split(Constants.SPACE);
            existValidate(mLine[0]);
            int summ = Integer.valueOf(mLine[0]);
            int[] coins = new int[mLine.length-1];
            for (int i =0; i<mLine.length-1;i++){
                existValidate(mLine[i+1]);
                int value = Integer.valueOf(mLine[i+1]);
                coinsContains(coins, value);
                coins[i]=value;
            }
            return new ExpressionCoins(summ, coins);
        }catch (NumberFormatException e){
            throw new ParserExceptions(Constants.Convert_EXCEPTION);
        }
    }

    private  void existValidate(String value){
        if (Constants.EMPTY.equals(value)){
            throw new ParserExceptions(Constants.EXIST_EXCEPTION);
        }
    }
    private void coinsContains(int[] coins, int value){
        for (int coin : coins){
            if (coin == value){
                throw new ParserExceptions(Constants.COINS_DUPLICATE_EXCEPTION);
            }
        }
    }
}
