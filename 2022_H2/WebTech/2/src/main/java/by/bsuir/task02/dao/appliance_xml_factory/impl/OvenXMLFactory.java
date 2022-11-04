package by.bsuir.task02.dao.appliance_xml_factory.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_xml_factory.ApplianceXMLFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Oven;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OvenXMLFactory extends ApplianceXMLFactory {

    @Override
    public Node createApplianceXML(Document document, Appliance appliance) {
        Oven oven = (Oven) appliance;
        Element ovenElement = document.createElement(ApplianceTagName.OVEN.name().toLowerCase().replace('_', '-'));

        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.PRICE.name(), oven.getPrice()));
        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.POWER_CONSUMPTION.name(), oven.getPowerConsumption()));
        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.WEIGHT.name(), oven.getWeight()));
        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.CAPACITY.name(), oven.getCapacity()));
        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.DEPTH.name(), oven.getDepth()));
        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.HEIGHT.name(), oven.getHeight()));
        ovenElement.appendChild(createElement(document, SearchCriteria.Oven.WIDTH.name(), oven.getWidth()));
        return ovenElement;
    }
}
