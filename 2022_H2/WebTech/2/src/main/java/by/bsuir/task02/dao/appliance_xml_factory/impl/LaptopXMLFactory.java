package by.bsuir.task02.dao.appliance_xml_factory.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_xml_factory.ApplianceXMLFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Laptop;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LaptopXMLFactory extends ApplianceXMLFactory {

    @Override
    public Node createApplianceXML(Document document, Appliance appliance) {
        Laptop laptop = (Laptop) appliance;
        Element laptopElement = document.createElement(ApplianceTagName.LAPTOP.name().toLowerCase().replace('_', '-'));

        laptopElement.appendChild(createElement(document, SearchCriteria.Laptop.PRICE.name(), laptop.getPrice()));
        laptopElement.appendChild(createElement(document, SearchCriteria.Laptop.BATTERY_CAPACITY.name(), laptop.getBatteryCapacity()));
        laptopElement.appendChild(createElement(document, SearchCriteria.Laptop.OS.name(), String.valueOf(laptop.getOs())));
        laptopElement.appendChild(createElement(document, SearchCriteria.Laptop.MEMORY_ROM.name(), laptop.getMemoryRom()));
        laptopElement.appendChild(createElement(document, SearchCriteria.Laptop.CPU.name(), laptop.getCpu()));
        laptopElement.appendChild(createElement(document, SearchCriteria.Laptop.DISPLAY_INCHES.name(), laptop.getDisplayInches()));
        return laptopElement;
    }

}
