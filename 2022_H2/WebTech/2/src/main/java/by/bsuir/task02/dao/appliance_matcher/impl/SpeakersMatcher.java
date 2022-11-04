package by.bsuir.task02.dao.appliance_matcher.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_matcher.ApplianceMatcherFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Speakers;
import by.bsuir.task02.entity.criteria.SearchCriteria;

public class SpeakersMatcher extends ApplianceMatcherFactory {
    @Override
    public boolean isMatchesCriteria(Appliance appliance, String criteriaName, Object value) {
        Speakers speakers = (Speakers) appliance;

        return switch (SearchCriteria.SpeakersWithPriceFilter.valueOf(criteriaName)) {
            case MORE_THAN_CURRENT_PRICE -> speakers.getPrice() > (double) value;
            case LESS_THAN_CURRENT_PRICE -> speakers.getPrice() < (double) value;
            case PRICE, EQUAL_CURRENT_PRICE -> speakers.getPrice() == (double) value;
            case NAME -> ApplianceTagName.SPEAKERS.equals(value);
            case POWER_CONSUMPTION -> (double) value == speakers.getPowerConsumption();
            case NUMBER_OF_SPEAKERS -> (int) value == speakers.getNumberOfSpeakers();
            case CORD_LENGTH -> (double) value == speakers.getCordLength();
        };
    }
}
