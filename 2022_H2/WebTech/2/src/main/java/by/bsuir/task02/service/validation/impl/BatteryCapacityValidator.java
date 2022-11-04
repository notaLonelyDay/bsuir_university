package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.service.validation.CriteriaValidator;

public class BatteryCapacityValidator implements CriteriaValidator {
    private final static double MIN_BATTERY_CAPACITY = 0.0;
    private final static double MAX_BATTERY_CAPACITY = 200000.0;

    @Override
    public boolean isCriteriaValid(Object value) {
        if (value instanceof Double) {
            double batteryCapacity = (Double) value;
            return ((batteryCapacity > MIN_BATTERY_CAPACITY) && (batteryCapacity < MAX_BATTERY_CAPACITY));
        } else {
            return false;
        }
    }
}
