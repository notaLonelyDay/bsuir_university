package by.bsuir.task02.dao.appliance_matcher;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_matcher.impl.*;
import by.bsuir.task02.entity.Appliance;

public abstract class ApplianceMatcherFactory {

    public abstract boolean isMatchesCriteria(Appliance appliance, String criteriaName, Object value);

    public static ApplianceMatcherFactory getMatcher(String applianceName) throws EnumConstantNotPresentException {
        try {
            return switch (ApplianceTagName.valueOf(applianceName)) {
                case LAPTOP -> new LaptopMatcher();
                case OVEN -> new OvenMatcher();
                case REFRIGERATOR -> new RefrigeratorMatcher();
                case SPEAKERS -> new SpeakersMatcher();
                case TABLET_PC -> new TabletPCMatcher();
                case VACUUM_CLEANER -> new VacuumCleanerMatcher();
            };
        } catch (IllegalArgumentException e) {
            throw new EnumConstantNotPresentException(ApplianceTagName.class, applianceName);
        }
    }

}
