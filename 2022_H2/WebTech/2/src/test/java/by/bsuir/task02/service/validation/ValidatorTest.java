package by.bsuir.task02.service.validation;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.entity.criteria.Criteria;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void isCriteriaValidWithValidParameters() {
        Criteria criteriaOven = new Criteria(ApplianceTagName.OVEN.name());
        criteriaOven.add(SearchCriteria.Oven.HEIGHT.name(), 45.0);
        criteriaOven.add(SearchCriteria.Oven.DEPTH.name(), 60.0);
        criteriaOven.add(SearchCriteria.Oven.POWER_CONSUMPTION.name(), 1500.0);

        Assert.assertTrue(Validator.isCriteriaValid(criteriaOven));
    }

    @Test
    public void isCriteriaValidWithInvalidParameters() {
        Criteria criteriaOven = new Criteria(ApplianceTagName.LAPTOP.name());
        criteriaOven.add(SearchCriteria.Laptop.OS.name(), "invalidValue");

        Assert.assertFalse(Validator.isCriteriaValid(criteriaOven));
    }

    @Test
    public void isCriteriaValidWithNullParameter() {
        Assert.assertFalse(Validator.isCriteriaValid(null));
    }
}