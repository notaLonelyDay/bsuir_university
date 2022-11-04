package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.service.validation.CriteriaValidator;

public class PowerConsumptionValidator implements CriteriaValidator {
    private final static double MIN_POWER_CONSUMPTION = 0.0;
    private final static double MAX_POWER_CONSUMPTION = 100000.0;

    @Override
    public boolean isCriteriaValid(Object value) {
        if (value instanceof Double) {
            double powerConsumption = (Double) value;
            return ((powerConsumption > MIN_POWER_CONSUMPTION) && (powerConsumption < MAX_POWER_CONSUMPTION));
        } else {
            return false;
        }
    }
}
