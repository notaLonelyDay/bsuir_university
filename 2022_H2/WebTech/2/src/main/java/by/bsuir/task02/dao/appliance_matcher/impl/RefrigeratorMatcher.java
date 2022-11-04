package by.bsuir.task02.dao.appliance_matcher.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_matcher.ApplianceMatcherFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Refrigerator;
import by.bsuir.task02.entity.criteria.SearchCriteria;

public class RefrigeratorMatcher extends ApplianceMatcherFactory {

    @Override
    public boolean isMatchesCriteria(Appliance appliance, String criteriaName, Object value) {
        Refrigerator refrigerator = (Refrigerator) appliance;

        return switch (SearchCriteria.RefrigeratorWithPriceFilter.valueOf(criteriaName)) {
            case MORE_THAN_CURRENT_PRICE -> refrigerator.getPrice() > (double) value;
            case LESS_THAN_CURRENT_PRICE -> refrigerator.getPrice() < (double) value;
            case PRICE, EQUAL_CURRENT_PRICE -> refrigerator.getPrice() == (double) value;
            case NAME -> ApplianceTagName.REFRIGERATOR.equals(value);
            case POWER_CONSUMPTION -> (double) value == refrigerator.getPowerConsumption();
            case WEIGHT -> refrigerator.getWeight() == (double) value;
            case FREEZER_CAPACITY -> refrigerator.getFreezerCapacity() == (double) value;
            case OVERALL_CAPACITY -> refrigerator.getOverallCapacity() == (double) value;
            case HEIGHT -> refrigerator.getHeight() == (double) value;
            case WIDTH -> refrigerator.getWidth() == (double) value;
        };
    }
}
