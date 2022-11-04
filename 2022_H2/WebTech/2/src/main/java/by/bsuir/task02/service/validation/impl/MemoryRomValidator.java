package by.bsuir.task02.service.validation.impl;

import by.bsuir.task02.service.validation.CriteriaValidator;

public class MemoryRomValidator implements CriteriaValidator {
    private final static double MIN_MEMORY_ROM = 0.0;
    private final static double MAX_MEMORY_ROM = 100000.0;

    @Override
    public boolean isCriteriaValid(Object value) {
        if (value instanceof Double) {
            double memoryRom = (Double) value;
            return ((memoryRom > MIN_MEMORY_ROM) && (memoryRom < MAX_MEMORY_ROM));
        } else {
            return false;
        }
    }
}
