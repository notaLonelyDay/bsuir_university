package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class ApplianceNameValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            ApplianceTagName.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }
}
