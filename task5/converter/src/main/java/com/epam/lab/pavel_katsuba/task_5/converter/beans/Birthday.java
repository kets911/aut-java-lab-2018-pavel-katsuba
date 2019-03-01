package com.epam.lab.pavel_katsuba.task_5.converter.beans;

import com.epam.lab.pavel_katsuba.task_5.converter.Constants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias(Constants.BIRTHDAY)
public class Birthday {
    @XStreamAlias(Constants.DAY)
    private final int day;
    @XStreamAlias(Constants.MONTH)
    private final int month;
    @XStreamAlias(Constants.YEAR)
    private final int year;
}
