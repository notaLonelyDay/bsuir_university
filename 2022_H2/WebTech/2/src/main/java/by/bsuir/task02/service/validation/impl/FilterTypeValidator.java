package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.entity.FilterType;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class FilterTypeValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            FilterType.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

}
