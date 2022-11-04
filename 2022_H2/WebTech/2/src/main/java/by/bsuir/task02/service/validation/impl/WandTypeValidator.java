package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.entity.WandType;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class WandTypeValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            WandType.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

}
