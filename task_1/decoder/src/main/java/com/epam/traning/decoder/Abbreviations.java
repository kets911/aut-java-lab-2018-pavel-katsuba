package com.epam.traning.decoder;

public enum Abbreviations {
    АСУ(){
        public String getFullName(){
            return "автоматизированная система управления";
        }
    }, ВУЗ(){
        public String getFullName(){
            return "высшее учебное заведение";
        }
    }, ГЭС(){
        public String getFullName(){
            return "гидроэлектростанция";
        }
    }, ОДУ(){
        public String getFullName(){
            return "обыкновенное дифференциальное уравнение";
        }
    }, ТАСС(){
        public String getFullName(){
            return "телеграфное агентство Советского Союза";
        }
    }, ЯОД(){
        public String getFullName(){
            return "язык описания данных";
        }
    };
    public abstract String getFullName();

}