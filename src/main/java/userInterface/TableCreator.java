package userInterface;

import domainEntities.*;
import engine.Common;

import java.util.*;

public class TableCreator {


    protected static void showProductsTable(List<Product> products) {
        String leftAlignFormat = "| %-5s | %-30s | %-6s | %-6s | %-6s | %-8s |%n";

        System.out.format("+-------+--------------------------------+--------+--------+--------+----------+%n");
        System.out.format("|  ID   |             PRODUCT            | VOLUME |  UNIT  |  PRICE | CURRENCY |%n");
        System.out.format("+-------+--------------------------------+--------+--------+--------+----------+%n");
       // System.out.format(leftAlignFormat,"ID","PRODUCT","FLAVOUR","VOLUME","UNIT","PRICE","CURRENCY" );

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.format(leftAlignFormat,
                    Integer.toString(i+1),
                    product.getProductName(),
                    Integer.toString(product.getVolume()),
                    product.getUnitType(),
                    Double.toString(product.getPrice()),
                    Common.getLocalCurrency());
        }
        System.out.format("+-------+--------------------------------+--------+--------+--------+----------+%n");

    }

    protected static void listEmployees(List<Employee> employees, Location locationToList, Date dateFrom, Date dateTo) {
        String leftAlignFormat = "| %-20s | %-20s | %-20s | %-15s | %-15s | %-13s |%n";
        System.out.println("Listing all employees for dates: "+dateFrom+" - "+dateTo+"\nLocation: "+locationToList+"\n");


        //name, id, position, start date, end date, service grade
        System.out.format("+----------------------+----------------------+----------------------+-------------------------------+-------------------------------+---------------+%n");
        System.out.format("|         NAME         |         ID           |       POSITION       |           START DATE          |            END DATE           | SERVICE GRADE |%n");
        System.out.format("+----------------------+----------------------+----------------------+-------------------------------+-------------------------------+---------------+%n");

        for(int i =0; i<employees.size();i++){
            Employee employee = employees.get(i);
            double serviceGrade = employee.getServiceGrade();

            System.out.format(leftAlignFormat,
                    employee.getName(),
                    employee.getIdNumber(),
                    employee.getPosition(),
                    employee.getStartDate(),
                    employee.getEndDate(),
                    serviceGrade+"%");
        }
        System.out.format("+----------------------+----------------------+----------------------+-------------------------------+-------------------------------+---------------+%n");


    }

    protected static void showCurrentOrderInTable(List<Product> products, double sum) {
        String leftAlignFormat = "| %-5s | %-30s | %-20s | %-6s | %-6s | %-6s | %-8s |%n";

        System.out.format("+-------+--------------------------------+----------------------+--------+--------+--------+----------+%n");
        System.out.format("|  ID   |             PRODUCT            |       FLAVOUR        | VOLUME |  UNIT  |  PRICE | CURRENCY |%n");
        System.out.format("+-------+--------------------------------+----------------------+--------+--------+--------+----------+%n");
        // System.out.format(leftAlignFormat,"ID","PRODUCT","FLAVOUR","VOLUME","UNIT","PRICE","CURRENCY" );


        List<Product> discountedProducts = new LinkedList<Product>();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String flavour="";
            double price = product.getPrice();
            if(price==0)
                discountedProducts.add(product);
            if(product.isFlavorEnabled()){
                Flavour f = product.getFlavour();
                if(f!=null)
                    flavour = f.getName();
                else
                    flavour="";
            }
            System.out.format(leftAlignFormat,
                    Integer.toString(i+1),
                    product.getProductName(),
                    flavour,
                    Integer.toString(product.getVolume()),
                    product.getUnitType(),
                    Double.toString(price),
                    Common.getLocalCurrency());
        }
        System.out.format("+-------+--------------------------------+----------------------+--------+--------+--------+----------+%n");
        if(!discountedProducts.isEmpty()){
            System.out.println(LocalisationStrings.discountTenthProduct()+":\n");
            for(Product p : discountedProducts)
                System.out.println(p.getProductName());
        }

        System.out.println("\n"+LocalisationStrings.sum()+": "+sum+" "+Common.getLocalCurrency());

    }

    protected static void listCustomers(List<Customer> customers, Location locationToList, Date dateFrom, Date dateTo) {
        System.out.println("Listing all customers for dates: "+dateFrom+" - "+dateTo+"\nLocation: "+locationToList  +"\n");

        String leftAlignFormat = "| %-20s | %-20s | %-20s | %-20s | %-30s | %-10s | %-50s |%n";

        System.out.format("+----------------------+----------------------+----------------------+----------------------+--------------------------------+------------+--------------------------------+%n");
        System.out.format("|         NAME         |       OCCUPATION     |        BARCODE       |           ID         |         DATE REGISTERED        | PURCHASES  |             ADDRESS            |%n");
        System.out.format("+----------------------+----------------------+----------------------+----------------------+--------------------------------+------------+--------------------------------+%n");
        // System.out.format(leftAlignFormat,"ID","PRODUCT","FLAVOUR","VOLUME","UNIT","PRICE","CURRENCY" );

        for (int i = 0; i < customers.size(); i++) {
            Customer customer= customers.get(i);
            System.out.format(leftAlignFormat,
                    customer.getName(),
                    customer.getOccupation(),
                    customer.getBarcode(),
                    customer.getIdNumber(),
                    customer.getRegisteredDate(),
                    customer.getTotalPurchases(),
                    customer.getAddress().toString()
                    );
        }
        System.out.format("+----------------------+----------------------+----------------------+----------------------+--------------------------------+------------+--------------------------------+%n");


    }

    protected static void listProductSales(HashMap<String,String> products, Location location, String dateFrom, String dateTo, double sum) {
        System.out.println("Listing all sales for dates: "+Common.formatDate(dateFrom)+" - "+Common.formatDate(dateTo)+"\nLocation: "+location  +"\n");

        String leftAlignFormat = "| %-30s | %-20s | %-20s |%n";


        System.out.format("+--------------------------------+----------------------+----------------------+%n");
        System.out.format("|            PRODUCT             |       UNITS SOLD     |      TOTAL VALUE     |%n");
        System.out.format("+--------------------------------+----------------------+----------------------+%n");
        // System.out.format(leftAlignFormat,"ID","PRODUCT","FLAVOUR","VOLUME","UNIT","PRICE","CURRENCY" );

        for(Map.Entry<String,String> entry : products.entrySet()){
            String[] vals = entry.getValue().split(",");
            String prodName = entry.getKey();
            System.out.format(leftAlignFormat,
                    prodName,
                    vals[0],
                    Integer.parseInt(vals[0])*Double.parseDouble(vals[1])
                    );
        }

        System.out.format("+--------------------------------+----------------------+----------------------+%n");

        System.out.println("\n"+LocalisationStrings.sum()+": "+sum+" "+Common.getLocalCurrency(location));


    }

    protected static void listProductSalesZipOrOccupation(List<Product> products, Location location, String zipOrOccupation, double sum) {
        String[] zipOrOcc = zipOrOccupation.split(",");
        System.out.println("Listing all sales for "+zipOrOcc[1]+": "+zipOrOcc[0]+"\nLocation: "+location  +"\n");

        String leftAlignFormat = "| %-20s | %-20s | %-20s |%n";


        System.out.format("+----------------------+----------------------+----------------------+%n");
        System.out.format("|        PRODUCT       |       UNITS SOLD     |      TOTAL VALUE     |%n");
        System.out.format("+----------------------+----------------------+----------------------+%n");
        // System.out.format(leftAlignFormat,"ID","PRODUCT","FLAVOUR","VOLUME","UNIT","PRICE","CURRENCY" );

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.format(leftAlignFormat,
                    product.getProductName(),
                    Integer.toString(product.getVolume()),
                    Double.toString(product.getPrice())
            );
        }
        System.out.format("+----------------------+----------------------+----------------------+%n");
        System.out.println("\n"+LocalisationStrings.sum()+": "+sum+" "+Common.getLocalCurrency(location));
    }

    public static void listOrdersByEmployee(Employee employee, List<Order> ordersByEmployee,Location location) {
        System.out.println("Listing all orders for: "+employee.getName()+"\nLocation: "+location  +"\n");

        String leftAlignFormat = "| %-20s | %-30s | %-20s | %-20s | %-20s |%n";


        System.out.format("+----------------------+--------------------------------+----------------------+----------------------+----------------------+%n");
        System.out.format("|          ID          |            DATE                |    CUSTOMER BARCODE  |        SUM ("+Common.getLocalCurrency(location)+")     |   EMPLOYEE DISCOUNT  |%n");
        System.out.format("+----------------------+--------------------------------+----------------------+----------------------+----------------------+%n");

        for (int i = 0; i < ordersByEmployee.size(); i++) {
            Order order = ordersByEmployee.get(i);
            String currency = Common.getLocalCurrency(order.getLocation());
            order.getEmployeeDiscount();
            System.out.format(leftAlignFormat,
                    i+1,
                    order.getOrderDate(),
                    order.getCustomerBarcode(),
                    order.getSum(),
                    order.getEmployeeDiscount()
            );
        }
        System.out.format("+----------------------+--------------------------------+----------------------+----------------------+----------------------+%n");

    }

    public static void showStock(HashMap<Product, Integer> productsInStock, Location location, Date dateFrom, Date dateTo) {
        System.out.println(LocalisationStrings.stock()+" - "+location+"\n"+dateFrom+" - "+dateTo);

        String leftAlignFormat = "| %-10s | %-30s | %-20s | %-20s |%n";


        System.out.format("+------------+--------------------------------+----------------------+----------------------+%n");
        System.out.format("|    ID      |            PRODUCT             |   NUMBER OF UNITS    |         VOLUME       |%n");
        System.out.format("+------------|--------------------------------+----------------------+----------------------+%n");

        int i =1;
        for(Product p : productsInStock.keySet()){
            int quantity = productsInStock.get(p);

            System.out.format(leftAlignFormat,
                    Integer.toString(i++),
                    p.getProductName(),
                    Integer.toString(quantity),
                    p.getVolume()+" "+p.getUnitType()
            );
        }
        System.out.format("+------------|--------------------------------+----------------------+----------------------+%n");

    }
}
