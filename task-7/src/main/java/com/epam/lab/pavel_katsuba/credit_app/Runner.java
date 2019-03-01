package com.epam.lab.pavel_katsuba.credit_app;

import com.epam.lab.pavel_katsuba.credit_app.JsonUtils.converters.BankDBManager;
import com.epam.lab.pavel_katsuba.credit_app.JsonUtils.converters.SettingConverter;
import com.epam.lab.pavel_katsuba.credit_app.beans.CreditInfo;
import com.epam.lab.pavel_katsuba.credit_app.beans.Data;
import com.epam.lab.pavel_katsuba.credit_app.beans.Settings;
import com.epam.lab.pavel_katsuba.credit_app.dao_impl.*;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.credit_app.services.CreditService;

import java.io.File;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        final String srcDirectory = args[0];
        File jsonFile = new File(srcDirectory + "db.json");
        DBManager<Data> bankDBManager = new BankDBManager(jsonFile);

        File settingFile = new File(srcDirectory + "settings.json");
        DBManager<Settings> settingConverter = new SettingConverter(settingFile);
        Settings settings = settingConverter.getFromDB();

        CreditService creditService = new CreditService(new UserDAOImpl(bankDBManager), new CreditDAOImpl(bankDBManager),
                new DiscountDaoImpl(bankDBManager), new EventDAOImpl(bankDBManager), new TransactionDAOImpl(bankDBManager));
        creditService.normalizeDB(settings, srcDirectory);
        List<CreditInfo> creditInfo = creditService.getCreditInfo(settings);
        for (CreditInfo info : creditInfo) {
            System.out.println(info + "\n");
        }
    }
}
