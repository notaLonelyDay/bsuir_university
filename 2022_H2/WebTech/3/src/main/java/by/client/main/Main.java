package main.java.by.client.main;

import main.java.by.client.dao.ClientDAOFactory;
import main.java.by.client.presentation.Presentation;
import main.java.by.client.service.ServiceClientFactory;

public class Main {
    public static void main(String[] args) {
        ServiceClientFactory factory = ServiceClientFactory.getInstance();
        ClientDAOFactory daoFactory = ClientDAOFactory.getInstance();
        Presentation presentation = new Presentation(factory.getStudentService(daoFactory.getStudentDAO()));
        presentation.show();
    }
}
