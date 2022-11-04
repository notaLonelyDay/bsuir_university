package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.entity.Color;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class ColorValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            Color.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

}
