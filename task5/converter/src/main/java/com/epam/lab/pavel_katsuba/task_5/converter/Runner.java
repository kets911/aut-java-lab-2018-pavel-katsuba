package com.epam.lab.pavel_katsuba.task_5.converter;


import com.epam.lab.pavel_katsuba.task_5.converter.beans.Birthday;
import com.epam.lab.pavel_katsuba.task_5.converter.beans.Birthplace;
import com.epam.lab.pavel_katsuba.task_5.converter.beans.Employee;
import com.epam.lab.pavel_katsuba.task_5.converter.beans.Jobs;
import com.epam.lab.pavel_katsuba.task_5.converter.services.PeopleDom;
import com.epam.lab.pavel_katsuba.task_5.converter.services.ReadWriteXml;
import lombok.NonNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Runner {


    public static void main(String[] args) {

        try {
            File xmlFile = new File(ClassLoader.getSystemResource("people.xml").getPath());
            PeopleDom dom = new PeopleDom(xmlFile);
            dom.setEmployee(new Employee(3, "Pav", "Kats", new Birthday(27, 07, 1990), new Birthplace("BP city")));
            int jobId = new Random(1).nextInt(Jobs.values().length);
            dom.changeWorkPlaceEmployee("I", Jobs.values()[jobId].toString());
            dom.printToConsole();
            dom.writeToXml(xmlFile);
            List<Employee> people = ReadWriteXml.unmarshalling(xmlFile);
            Collections.sort(people, new Comparator<Employee>() {
                @Override
                public int compare(@NonNull Employee o1, @NonNull Employee o2) {
                    Birthday o1Birthday = o1.getBirthday();
                    Birthday o2Birthday = o2.getBirthday();
                    LocalDate o1Date = LocalDate.of(o1Birthday.getYear(), o1Birthday.getMonth(), o1Birthday.getDay());
                    LocalDate o2Date = LocalDate.of(o2Birthday.getYear(), o2Birthday.getMonth(), o2Birthday.getDay());
                    return o1Date.compareTo(o2Date);
                }
            });
            System.out.println(people);
            String peopleXml = ReadWriteXml.marshalling(people);
            ReadWriteXml.saveToFile(peopleXml, xmlFile);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
