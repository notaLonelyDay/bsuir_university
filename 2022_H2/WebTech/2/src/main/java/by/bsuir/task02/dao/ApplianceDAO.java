package by.bsuir.task02.dao;

import by.bsuir.task02.entity.Appliance;
import by.bsuir.task02.entity.criteria.Criteria;

import java.util.List;

public interface ApplianceDAO {
    List<Appliance> find(Criteria criteria) throws DaoException;

    boolean add(String applianceName, Appliance appliance) throws DaoException;
}
