package com.epam.lab.pavel_katsuba.task_5.converter.services;

import com.epam.lab.pavel_katsuba.task_5.converter.beans.Birthday;
import com.epam.lab.pavel_katsuba.task_5.converter.beans.Birthplace;
import com.epam.lab.pavel_katsuba.task_5.converter.beans.Employee;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteXml {

    public static String marshalling(List<Employee> object) {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("people", List.class);
        xStream.processAnnotations(Employee.class);
        return xStream.toXML(object);
    }

    public static void saveToFile(String xml, File xmlFile) throws IOException {
        Writer writer = new FileWriter(xmlFile);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write(xml);
        writer.close();
    }


    public static List<Employee> unmarshalling(File file) {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("people", List.class);
        xStream.alias("person", Employee.class);
        xStream.aliasField("name", Employee.class, "name");
        xStream.aliasField("surname", Employee.class, "surname");
        xStream.aliasField("birthday", Employee.class, "birthday");
        xStream.aliasField("birthplace", Birthplace.class, "birthplace");
        xStream.aliasField("work", Employee.class, "workPlace");
        xStream.aliasField("day", Birthday.class, "day");
        xStream.aliasField("month", Birthday.class, "month");
        xStream.aliasField("year", Birthday.class, "year");
        xStream.aliasAttribute(Employee.class, "id", "ID");
        xStream.aliasAttribute(Birthplace.class, "city", "city");
        xStream.registerConverter((Converter) new EncodedByteArrayConverter());
        return (ArrayList<Employee>) xStream.fromXML(file);
    }
}
