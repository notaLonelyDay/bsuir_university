package by.bsuir.task02.main;

import by.bsuir.task02.entity.Appliance;

import java.util.List;

public class PrinterApplianceInfo {
    private static final String APPLIANCES_LIST_NULL_EXCEPTION_MSG = "AppliancesList is null.\nAppliancesList cannot be null!";
    private static final String APPLIANCES_LIST_EMPTY_MSG = "AppliancesList is empty (appliances not found).";

    public static void print(List<Appliance> appliances) {
        if (appliances == null) {
            throw new NullPointerException(APPLIANCES_LIST_NULL_EXCEPTION_MSG);
        } else if (appliances.isEmpty()) {
            System.out.println(APPLIANCES_LIST_EMPTY_MSG);
        } else {
            for (Appliance appliance : appliances) {
                System.out.println(appliance);
            }
        }
    }

}
