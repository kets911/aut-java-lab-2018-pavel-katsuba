package com.epam.lab.pavel_katsuba.credit_app.utils;

import com.epam.lab.pavel_katsuba.credit_app.beans.Credit;
import com.epam.lab.pavel_katsuba.credit_app.beans.CreditInfo;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.CreditStatus;

import java.util.Formatter;
import java.util.List;

public class Printer {
    public static void print(List<CreditInfo> creditInfo) {
        Formatter formatter = new Formatter();
        formatter.format("Credit id\tUser id\t\tUser name\t\tTransaction count\tDebt\t\tPeriod\tStatus\n");
        String format = "%-9d\t%-8d\t%-15s\t%-18s\t%-8s\t%-5s\t%s\n";
        for (CreditInfo info : creditInfo) {
            Credit credit = info.getCredit();
            formatter.format(format, credit == null ? null : credit.getId(),
                    credit == null ? null : credit.getUserId(),
                    info.getUser().getName() + " " + info.getUser().getSecondName(),
                    info.getTransactionCount() == 0 ? "null" : info.getTransactionCount(),
                    info.getDebt(),
                    credit == null ? "null" : credit.getPeriod(),
                    info.getStatus() == CreditStatus.DONE ? info.getStatus() + " " + info.getDoneDate() : info.getStatus());
        }
        System.out.println(formatter.toString());
    }
}
