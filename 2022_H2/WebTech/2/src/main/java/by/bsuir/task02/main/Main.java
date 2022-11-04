package by.bsuir.task02.main;

import by.bsuir.task02.dao.constant.ApplianceTagName;
import by.bsuir.task02.entity.*;
import by.bsuir.task02.entity.criteria.Criteria;
import by.bsuir.task02.entity.criteria.SearchCriteria;
import by.bsuir.task02.dao.DaoException;
import by.bsuir.task02.service.ServiceException;
import by.bsuir.task02.service.ApplianceService;
import by.bsuir.task02.service.ServiceFactory;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ServiceException, DaoException {
        List<Appliance> appliances;

        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            ApplianceService service = factory.getApplianceService();

            //////////////////////////////////////////////////////////////////

            Criteria criteriaLaptop = new Criteria(ApplianceTagName.LAPTOP.name());
            criteriaLaptop.add(SearchCriteria.PriceFilter.LESS_THAN_CURRENT_PRICE.name(), 1000.0);

            appliances = service.find(criteriaLaptop);

            System.out.println("Find all laptops that price is less 1000.0.");
            PrinterApplianceInfo.print(appliances);

            //////////////////////////////////////////////////////////////////

            Criteria criteriaOven = new Criteria(ApplianceTagName.OVEN.name());
            criteriaOven.add(SearchCriteria.Oven.HEIGHT.name(), 45.0);
            criteriaOven.add(SearchCriteria.Oven.DEPTH.name(), 60.0);

            appliances = service.find(criteriaOven);

            System.out.println("\nFind all ovens that HEIGHT is 45.0, DEPTH is 60.0.");
            PrinterApplianceInfo.print(appliances);

            //////////////////////////////////////////////////////////////////

            Criteria criteriaTabletPC = new Criteria(ApplianceTagName.TABLET_PC.name());
            criteriaTabletPC.add(SearchCriteria.TabletPC.COLOR.name(), "BLUE");
            criteriaTabletPC.add(SearchCriteria.TabletPC.DISPLAY_INCHES.name(), 15.0);
            criteriaTabletPC.add(SearchCriteria.TabletPC.MEMORY_ROM.name(), 12000.0);

            appliances = service.find(criteriaTabletPC);

            System.out.println("\nFind all tabletPC-s that COLOR is BLUE, DISPLAY_INCHES is 15.0, MEMORY_ROM is 12000.0.");
            PrinterApplianceInfo.print(appliances);

            //////////////////////////////////////////////////////////////////

            Criteria criteriaSpeakers = new Criteria(ApplianceTagName.SPEAKERS.name());
            criteriaSpeakers.add(SearchCriteria.PriceFilter.MORE_THAN_CURRENT_PRICE.name(), 300.0);

            appliances = service.find(criteriaSpeakers);

            System.out.println("\nFind all speakers that price is more than 300.0.");
            PrinterApplianceInfo.print(appliances);

            //////////////////////////////////////////////////////////////////

            Criteria criteriaWithEmptyGroupSearchName = new Criteria("");
            criteriaWithEmptyGroupSearchName.add(SearchCriteria.PriceFilter.MORE_THAN_CURRENT_PRICE.name(), 300.0);

            appliances = service.find(criteriaWithEmptyGroupSearchName);

            System.out.println("\nFind all appliances that price is more than 300.0 because criteria has empty groupSearchName.");
            PrinterApplianceInfo.print(appliances);

            //////////////////////////////////////////////////////////////////

            service.add(ApplianceTagName.LAPTOP.name(), new Laptop(1000.0, 1000.0, OS.LINUX, 100.0, CPU.AMD_RYZEN_5_5600X, 13.6));

        } catch (DaoException | ServiceException | NullPointerException exception) {
            System.err.println(exception.getLocalizedMessage());
            exception.printStackTrace();
        }

    }

}
