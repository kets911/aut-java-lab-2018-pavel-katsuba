package com.epam.traning.decorator;

import com.epam.traning.decorator.interfaces.Drink;

public class Runner {
    public static void main(String[] args){
        Sugar sugar = new Sugar(1);
        Milk milk = new Milk(2);
        Drink tea = new Tea(5);
        System.out.println(tea);
        Drink cofe = new Cofe(8);
        System.out.println(cofe);
        Drink sugarTea = new SugarDrink(tea, sugar);
        System.out.println(sugarTea);
        Drink milkTea = new MilkDrink(tea, milk);
        System.out.println(milkTea);
        Drink sugarMilkTea = new MilkDrink(sugarTea, milk);
        System.out.println(sugarMilkTea);
        Drink milkCofe = new MilkDrink(cofe, milk);
        System.out.println(milkCofe);
    }
}
