package main.java.by.client.dao;

import main.java.by.client.dao.impl.StudentClientDAOImpl;

public class ClientDAOFactory {
    private static final ClientDAOFactory instance = new ClientDAOFactory();

    private ClientDAOFactory() {}

    public StudentClientDAO getStudentDAO() {
        return new StudentClientDAOImpl();
    }

    public static ClientDAOFactory getInstance() {
        return instance;
    }
}
