package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.service.validation.CriteriaValidator;

public class PriceValidator implements CriteriaValidator {
    private final static double MIN_PRICE = 0.0;

    @Override
    public boolean isCriteriaValid(Object value) {
        if (value instanceof Double) {
            return (Double) value > MIN_PRICE;
        } else
            return false;
    }
}
