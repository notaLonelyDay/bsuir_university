package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.service.validation.CriteriaValidator;

public class CordLengthValidator implements CriteriaValidator {
    private final static double MIN_CORD_LENGTH = 0.0;
    private final static double MAX_CORD_LENGTH = 100.0;

    @Override
    public boolean isCriteriaValid(Object value) {
        if (value instanceof Double) {
            double cordLength = (Double) value;
            return ((cordLength > MIN_CORD_LENGTH) && (cordLength < MAX_CORD_LENGTH));
        } else {
            return false;
        }
    }
}
