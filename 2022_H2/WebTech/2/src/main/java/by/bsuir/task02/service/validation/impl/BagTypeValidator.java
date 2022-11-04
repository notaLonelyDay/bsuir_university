package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.entity.BagType;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class BagTypeValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            BagType.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

}
