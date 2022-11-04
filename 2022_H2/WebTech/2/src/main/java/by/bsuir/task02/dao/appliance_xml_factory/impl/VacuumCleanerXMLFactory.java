package by.bsuir.task02.dao.appliance_xml_factory.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_xml_factory.ApplianceXMLFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.VacuumCleaner;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class VacuumCleanerXMLFactory extends ApplianceXMLFactory {

    @Override
    public Node createApplianceXML(Document document, Appliance appliance) {
        VacuumCleaner vacuumCleaner = (VacuumCleaner) appliance;
        Element vacuumCleanerElement = document.createElement(ApplianceTagName.VACUUM_CLEANER.name().toLowerCase().replace('_', '-'));

        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.PRICE.name(), vacuumCleaner.getPrice()));
        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.POWER_CONSUMPTION.name(), vacuumCleaner.getPowerConsumption()));
        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.FILTER_TYPE.name(), vacuumCleaner.getFilterType()));
        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.BAG_TYPE.name(), vacuumCleaner.getBagType()));
        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.WAND_TYPE.name(), vacuumCleaner.getWandType()));
        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.MOTOR_SPEED_REGULATION.name(), vacuumCleaner.getMotorSpeedRegulation()));
        vacuumCleanerElement.appendChild(createElement(document, SearchCriteria.VacuumCleaner.CLEANING_WIDTH.name(), vacuumCleaner.getCleaningWidth()));
        return vacuumCleanerElement;
    }
}
