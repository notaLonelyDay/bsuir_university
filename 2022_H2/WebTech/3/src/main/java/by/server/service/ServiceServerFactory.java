package main.java.by.server.service;

import main.java.by.server.dao.StudentServerDAO;
import main.java.by.server.service.impl.StudentServerServiceImpl;

public class ServiceServerFactory {
    private static final ServiceServerFactory instance = new ServiceServerFactory();

    private ServiceServerFactory() {}

    public StudentServerService getStudentService(StudentServerDAO studentDAO) {

        return new StudentServerServiceImpl(studentDAO);
    }

    public static ServiceServerFactory getInstance() {
        return instance;
    }
}
