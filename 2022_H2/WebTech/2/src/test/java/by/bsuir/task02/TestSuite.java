package by.bsuir.task02;

import by.bsuir.task02.service.impl.ApplianceServiceImplTest;
import by.bsuir.task02.service.validation.ValidatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({ApplianceServiceImplTest.class, ValidatorTest.class})
@RunWith(Suite.class)
public class TestSuite {
}

