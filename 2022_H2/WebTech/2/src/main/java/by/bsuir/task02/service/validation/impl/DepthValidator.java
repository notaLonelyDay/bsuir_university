package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.service.validation.CriteriaValidator;

public class DepthValidator implements CriteriaValidator {
    private final static double MIN_DEPTH = 0.0;
    private final static double MAX_DEPTH = 300.0;

    @Override
    public boolean isCriteriaValid(Object value) {
        if (value instanceof Double) {
            double depth = (Double) value;
            return ((depth > MIN_DEPTH) && (depth < MAX_DEPTH));
        } else {
            return false;
        }
    }
}
