package by.bsuir.task02.service;

import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.criteria.Criteria;
import by.bsuir.task02.dao.DaoException;

import java.util.List;

public interface ApplianceService {

    List<Appliance> find(Criteria criteria) throws DaoException, ServiceException;

    boolean add(String applianceName, Appliance appliance) throws DaoException, ServiceException;

}
