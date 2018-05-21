package userInterface;

import domainEntities.Employee;
import domainEntities.Location;
import domainEntities.Product;
import engine.Common;

import java.util.List;

public class TableCreator {


    public static void showProductsTable(List<Product> products) {
        String leftAlignFormat = "| %-5s | %-20s | %-6s | %-6s | %-6s | %-8s |%n";

        System.out.format("+-------+----------------------+--------+--------+--------+----------+%n");
        System.out.format("|  ID   |        PRODUCT       | VOLUME |  UNIT  |  PRICE | CURRENCY |%n");
        System.out.format("+-------+----------------------+--------+--------+--------+----------+%n");
       // System.out.format(leftAlignFormat,"ID","PRODUCT","FLAVOUR","VOLUME","UNIT","PRICE","CURRENCY" );
        Location location = Common.getCurrentLocation();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.format(leftAlignFormat,
                    Integer.toString(i+1),
                    product.getProductName(),
                    Integer.toString(product.getVolume()),
                    product.getUnitType(),
                    Double.toString(product.getPrice(location)),
                    Common.getLocalCurrency());
        }
        System.out.format("+-------+----------------------+--------+--------+--------+----------+%n");

    }

    public static void listEmployees(List<Employee> employees) {
        String leftAlignFormat = "| %-20s | %-20s | %-20s | %-15s | %-15s | %-13s |%n";

        String id = Common.getLocalId();
        //name, id, position, start date, end date, service grade
        System.out.format("+----------------------+----------------------+----------------------+-----------------+-----------------+---------------+%n");
        System.out.format("|         NAME         |         ID           |       POSITION       |   START DATE    |   END DATE      | SERVICE GRADE |%n");
        System.out.format("+----------------------+----------------------+----------------------+-----------------+-----------------+---------------+%n");

        for(int i =0; i<employees.size();i++){
            Employee employee = employees.get(i);
            double serviceGrade = employee.getServiceGrade()*100;

            System.out.format(leftAlignFormat,
                    employee.getName(),
                    employee.getIdNumber(),
                    employee.getPosition(),
                    employee.getStartDate(),
                    employee.getEndDate(),
                    serviceGrade+"%");
        }
        System.out.format("+----------------------+----------------------+----------------------+-----------------+-----------------+---------------+%n");


    }
}
