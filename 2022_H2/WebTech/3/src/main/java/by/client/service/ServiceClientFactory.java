package main.java.by.client.service;

import main.java.by.client.dao.StudentClientDAO;
import main.java.by.client.service.impl.StudentClientServiceImpl;

public class ServiceClientFactory {
    private static final ServiceClientFactory instance = new ServiceClientFactory();

    private ServiceClientFactory() {}

    public StudentClientService getStudentService(StudentClientDAO studentDAO) {

        return new StudentClientServiceImpl(studentDAO);
    }

    public static ServiceClientFactory getInstance() {
        return instance;
    }
}
