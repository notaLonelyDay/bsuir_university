package by.bsuir.task02.dao.appliance_xml_factory.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_xml_factory.ApplianceXMLFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Refrigerator;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RefrigeratorXMLFactory extends ApplianceXMLFactory {
    @Override
    public Node createApplianceXML(Document document, Appliance appliance) {
        Refrigerator refrigerator = (Refrigerator) appliance;
        Element refrigeratorElement = document.createElement(ApplianceTagName.REFRIGERATOR.name().toLowerCase().replace('_', '-'));

        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.PRICE.name(), refrigerator.getPrice()));
        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.POWER_CONSUMPTION.name(), refrigerator.getPowerConsumption()));
        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.WEIGHT.name(), refrigerator.getWeight()));
        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.FREEZER_CAPACITY.name(), refrigerator.getFreezerCapacity()));
        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.OVERALL_CAPACITY.name(), refrigerator.getOverallCapacity()));
        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.HEIGHT.name(), refrigerator.getHeight()));
        refrigeratorElement.appendChild(createElement(document, SearchCriteria.Refrigerator.WIDTH.name(), refrigerator.getWidth()));
        return refrigeratorElement;
    }
}
