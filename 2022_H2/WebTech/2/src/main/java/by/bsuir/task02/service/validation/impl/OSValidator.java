package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.entity.OS;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class OSValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            OS.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

}
