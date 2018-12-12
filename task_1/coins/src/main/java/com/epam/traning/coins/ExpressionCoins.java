package com.epam.traning.coins;

import com.epam.traning.coins.exceptions.ExpressionCoinsException;

public class ExpressionCoins {
    private int summ;
    private int[] coins;

    public ExpressionCoins(int summ, int[] coins) {
        setSumm(summ);
        setCoins(coins);
    }

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        if (summ<=0){
            throw new ExpressionCoinsException(Constants.WRONG_SUMM_EXCEPTION);
        }
        this.summ = summ;
    }

    public int[] getCoins() {
        return coins;
    }

    public void setCoins(int[] coins) {
        if (coins.length == 0){
            throw new ExpressionCoinsException(Constants.WRONG_COINS_EXCEPTION);
        }
        this.coins = coins;
    }
}
