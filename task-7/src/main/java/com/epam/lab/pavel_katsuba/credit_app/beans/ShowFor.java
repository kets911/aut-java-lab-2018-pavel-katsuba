package com.epam.lab.pavel_katsuba.credit_app.beans;

import com.epam.lab.pavel_katsuba.credit_app.beans.enums.ShowType;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.ShowForMatcher;

public class ShowFor {
    private ShowType type;
    private ShowForMatcher showForMatcher;

    public ShowFor() {
    }

    public ShowFor(ShowType type, ShowForMatcher showForMatcher) {
        this.type = type;
        this.showForMatcher = showForMatcher;
    }

    public ShowType getType() {
        return type;
    }

    public void setType(ShowType type) {
        this.type = type;
    }

    public ShowForMatcher getShowForMatcher() {
        return showForMatcher;
    }

    public void setShowForMatcher(ShowForMatcher showForMatcher) {
        this.showForMatcher = showForMatcher;
    }
}
