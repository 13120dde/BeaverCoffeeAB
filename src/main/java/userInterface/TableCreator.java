package userInterface;

import domainEntities.Product;

import java.util.List;

public class TableCreator {


    public static void showProductsTable(List<Product> products) {
        String leftAlignFormat = "| %-5s | %-20s | %-20s | %-10s | %-10s | %-10s |%n";

        System.out.format("+-----+---------+---------+--------+------+-------+----------+%n");
        System.out.format("| ID  | PRODUCT | FLAVOUR | VOLUME | UNIT | PRICE | CURRENCY |%n");
        System.out.format("+-----+---------+---------+--------+------+-------+----------+%n");
        for (int i = 0; i < products.size(); i++) {
           // System.out.format(leftAlignFormat, );
        }
        System.out.format("+-----------------+------+%n");
    }
}
