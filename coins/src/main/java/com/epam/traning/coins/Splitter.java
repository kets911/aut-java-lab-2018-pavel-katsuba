package com.epam.traning.coins;

import com.epam.traning.coins.exceptions.GroupException;

import java.util.ArrayList;
import java.util.List;

public class Splitter {
    private int[] coins;
    private List<String> results;

    private void recSplit(int summ, int coinIndex, String result){
        if (coinIndex >= 0) {
            int coin = coins[coinIndex];
            if (summ-coin<0){
                recSplit(summ, coinIndex-1, result);
            }else {
                if (summ-coin>0) {
                    recSplit(summ - coin, coinIndex, result + Constants.SPACE + coin);
                    recSplit(summ, coinIndex - 1, result);
                }else {
                    recSplit(summ, coinIndex-1, result);
                    results.add(result + Constants.SPACE + coin);
                }
            }
        }
    }
    public List<String> split(ExpressionCoins expression){
        this.coins =expression.getCoins();
        this.results = new ArrayList<>();
        recSplit(expression.getSumm(), coins.length-1, Constants.EMPTY);
        if (results.isEmpty()){
            throw new GroupException(Constants.SPLIT_EXCEPTION);
        }
        return results;
    }
}
