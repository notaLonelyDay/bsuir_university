package by.bsuir.task02.dao.appliance_factory.impl;

import by.bsuir.task02.dao.appliance_factory.ApplianceFactory;
import by.bsuir.task02.dao.constant.DaoExceptionMessage;
import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.Speakers;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SpeakersFactory extends ApplianceFactory {
    private double price;
    private double powerConsumption;
    private int numberOfSpeakers;
    private double cordLength;

    @Override
    public Appliance createAppliance(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String value = nodeList.item(i).getTextContent();
                String speakersSearchCriteria = nodeList.item(i).getNodeName().toUpperCase().replace('-', '_');

                switch (SearchCriteria.Speakers.valueOf(speakersSearchCriteria)) {
                    case PRICE -> price = Double.parseDouble(value);
                    case POWER_CONSUMPTION -> powerConsumption = Double.parseDouble(value);
                    case NUMBER_OF_SPEAKERS -> numberOfSpeakers = Integer.parseInt(value);
                    case CORD_LENGTH -> cordLength = Double.parseDouble(value);
                    default -> throw new IllegalArgumentException(DaoExceptionMessage.ILLEGAL_ARGUMENT_APPLIANCE_FACTORY_EXCEPTION_MSG.getMessage());
                }
            }
        }

        return new Speakers(price, powerConsumption, numberOfSpeakers, cordLength);
    }

}
