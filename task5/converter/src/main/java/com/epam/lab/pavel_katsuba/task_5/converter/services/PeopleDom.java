package com.epam.lab.pavel_katsuba.task_5.converter.services;

import com.epam.lab.pavel_katsuba.task_5.converter.Constants;
import com.epam.lab.pavel_katsuba.task_5.converter.beans.Employee;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class PeopleDom {
    private static final String INDENT = "4";
    private Document document;
    private DocumentBuilder builder;

    public PeopleDom(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        this.builder = factory.newDocumentBuilder();
        this.document = builder.parse(xmlFile);
    }

    public void setEmployee(Employee employee){
        Document newDoc = builder.newDocument();
        Element newRoot = newDoc.createElement(Constants.PEOPLE);

        NodeList persons = document.getElementsByTagName(Constants.PERSON);
        Element person;
        for (int i=0; i<persons.getLength(); i++){
            person = (Element) persons.item(i);
            Integer personId = Integer.valueOf(person.getAttribute(Constants.ID));
            if (personId == employee.getId()){
                newRoot.appendChild(newDoc.importNode(getNewPerson(employee), true));
            }
            if (personId >= employee.getId()){
                personId++;
                person.setAttribute(Constants.ID, personId.toString());

            }
            newRoot.appendChild(newDoc.importNode(person, true));
        }

        newDoc.appendChild(newRoot);
        document = newDoc;
        document.getDocumentElement().normalize();
    }

    private Element getNewPerson(Employee employee) {

        Element newPerson = document.createElement(Constants.PERSON);
        newPerson.setAttribute(Constants.ID, String.valueOf(employee.getId()));
        Element name = document.createElement(Constants.NAME);
        Element surname = document.createElement(Constants.SURNAME);

        Element birthday = document.createElement(Constants.BIRTHDAY);
        Element day = document.createElement(Constants.DAY);
        Element month = document.createElement(Constants.MONTH);
        Element year = document.createElement(Constants.YEAR);

        Element birthplace = document.createElement(Constants.BIRTHPLACE);
        birthplace.setAttribute(Constants.CITY, employee.getBirthplace().getCity());

        name.appendChild(document.createTextNode(employee.getName()));
        surname.appendChild(document.createTextNode(employee.getSurname()));

        day.appendChild(document.createTextNode(String.valueOf(employee.getBirthday().getDay())));
        month.appendChild(document.createTextNode(String.valueOf(employee.getBirthday().getMonth())));
        year.appendChild(document.createTextNode(String.valueOf(employee.getBirthday().getYear())));
        birthday.appendChild(day);
        birthday.appendChild(month);
        birthday.appendChild(year);

        newPerson.appendChild(name);
        newPerson.appendChild(surname);
        newPerson.appendChild(birthday);
        newPerson.appendChild(birthplace);
        return newPerson;
    }

    public void changeWorkPlaceEmployee(String firstNameChar, String workPlace){
        NodeList persons = document.getElementsByTagName(Constants.PERSON);
        Element person;
        for (int i=0; i<persons.getLength(); i++) {
            person = (Element) persons.item(i);
            String name = person.getElementsByTagName(Constants.NAME).item(0).getTextContent();
            if (name.split("")[0].equals(firstNameChar)){
                person.getElementsByTagName(Constants.WORK).item(0).setTextContent(workPlace);
            }
        }
    }

    public void printToConsole() throws IOException {
        OutputFormat format = new OutputFormat(document);
        format.setIndenting(true);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);
        System.out.println(out);
    }

    public void writeToXml(File xmlFile) throws TransformerException, FileNotFoundException {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer transformer = tranFactory.newTransformer();
        Source src = new DOMSource(document);
        Result dest = new StreamResult(new FileOutputStream(xmlFile));
        transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, INDENT);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(src, dest);
    }



    public Document getDocument(){
        return document;
    }
}
