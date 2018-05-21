package engine;

import database.MongoDb;
import domainEntities.*;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    private MongoDb database;
    private Employee employee;

    public Controller(MongoDb database) {
        this.database=database;
        //TODO set location after user logged in, based on users location
        Common.setCurrentLocation(Location.SWEDEN);
    }

    public boolean login(String userName, String password) {
       employee = new Employee();
        // employee = database.login(userName,password);
        /*if(employee==null)
            return false;
        */return true;
    }

    public EmployePosition getCurrentUserPosition() {
        return employee.getPosition();
    }

    public void logout() {
        employee=null;
    }

    public LinkedList<Product> getAvailableProducts() {
        LinkedList<Product> products = new LinkedList<Product>();
        products.add(new Product(1,"Coffe","l","roasted",39.90, 50));
        products.add(new Product(2,"Tea","l","herbal",29.90, 50));
        products.add(new Product(3,"Latte","l","vanilla",49.90, 50));
        products.add(new Product(4,"Irish Cream","l","cognac",39.90, 50));
        return products;
    }

    /**
     *
     * @param productsInOrder :
     * @param barcode : String
     */
    public void registerOrder(List<Product> productsInOrder, String barcode) {


    }

    public boolean registerNewCustomer(String name, String custId, String occupation, String address) {
        return false;
    }
}
