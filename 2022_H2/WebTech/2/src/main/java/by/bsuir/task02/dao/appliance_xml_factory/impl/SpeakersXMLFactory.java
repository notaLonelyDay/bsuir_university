package by.bsuir.task02.dao.appliance_xml_factory.impl;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.dao.appliance_xml_factory.ApplianceXMLFactory;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Speakers;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
public class SpeakersXMLFactory extends ApplianceXMLFactory {

    @Override
    public Node createApplianceXML(Document document, Appliance appliance) {
        Speakers speakers = (Speakers) appliance;
        Element speakersElement = document.createElement(ApplianceTagName.SPEAKERS.name().toLowerCase().replace('_', '-'));

        speakersElement.appendChild(createElement(document, SearchCriteria.Speakers.PRICE.name(), speakers.getPrice()));
        speakersElement.appendChild(createElement(document, SearchCriteria.Speakers.POWER_CONSUMPTION.name(), speakers.getPowerConsumption()));
        speakersElement.appendChild(createElement(document, SearchCriteria.Speakers.NUMBER_OF_SPEAKERS.name(), speakers.getNumberOfSpeakers()));
        speakersElement.appendChild(createElement(document, SearchCriteria.Speakers.CORD_LENGTH.name(), speakers.getCordLength()));
        return speakersElement;
    }
}
