package com.epam.lab.pavel_katsuba.task_5.converter.beans;

import com.epam.lab.pavel_katsuba.task_5.converter.Constants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias(Constants.BIRTHPLACE)
public class Birthplace {
    @XStreamAsAttribute
    private final String city;
}
