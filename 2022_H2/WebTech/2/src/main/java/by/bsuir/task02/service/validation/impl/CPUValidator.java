package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.entity.CPU;
import by.bsuir.task02.service.validation.CriteriaValidator;

public class CPUValidator implements CriteriaValidator {

    @Override
    public boolean isCriteriaValid(Object value) {
        try {
            CPU.valueOf((String) value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

}
