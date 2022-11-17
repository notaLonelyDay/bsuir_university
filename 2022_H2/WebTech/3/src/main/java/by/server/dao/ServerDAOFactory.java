package main.java.by.server.dao;

import main.java.by.server.dao.impl.StudentServerDAOImpl;

public class ServerDAOFactory {
    private static final ServerDAOFactory instance = new ServerDAOFactory();

    private ServerDAOFactory() {}

    public StudentServerDAO getStudentDAO() {
        return new StudentServerDAOImpl();
    }

    public static ServerDAOFactory getInstance() {
        return instance;
    }
}
