package com.epam.lab.pavel_katsuba.credit_app.interfaces;

import java.time.LocalDate;

public interface DiscountPeriod {
    boolean isHit(LocalDate date);
}
