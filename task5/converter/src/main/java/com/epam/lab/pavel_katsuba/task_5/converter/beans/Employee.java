package com.epam.lab.pavel_katsuba.task_5.converter.beans;

import com.epam.lab.pavel_katsuba.task_5.converter.Constants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import lombok.NonNull;

@Data
@XStreamAlias(Constants.PERSON)
public class Employee {
    @XStreamAlias(Constants.ID)
    @XStreamAsAttribute
    private final int id;
    @XStreamAlias(Constants.NAME)
    private final String name;
    @XStreamAlias(Constants.SURNAME)
    private final String surname;
    @NonNull
    @XStreamAlias(Constants.BIRTHDAY)
    private final Birthday birthday;
    @XStreamAlias(Constants.BIRTHPLACE)
    private final Birthplace birthplace;
    @XStreamAlias(Constants.WORK)
    private String workPlace;
}
