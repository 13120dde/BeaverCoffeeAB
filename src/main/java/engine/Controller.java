package engine;

import database.MongoDb;
import domainEntities.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    private MongoDb database;
    private Employee employee;
    private boolean employeeDiscount = false, fillDBWithProducts = false;

    public Controller(MongoDb database) {
        this.database=database;

        //Just for filling db with products for the very first time
        if(fillDBWithProducts){
            fillDbWithInitialVals();
        }
        Common.setCurrentLocation(Location.ENGLAND);
    }

    private void fillDbWithInitialVals() {
        List<Product> products = BeaverProducts.getDomainProducts();
        for(Product p : products){
            boolean ok = database.addProduct(p);
            if(!ok)
                System.err.println("Failed to add product to db");
        }
        List<Flavour> flavours = BeaverProducts.getDomainFlavours();
        for(Flavour f: flavours){
            boolean ok = database.addFlavour(f);
            if(!ok)
                System.err.println("Failed to add flavour to db");
        }
        Employee employeeEngland = new Employee("employeeEng",
                "19840309****",
                Location.ENGLAND,
                100,
                new Date(),
                EmployePosition.EMPLOYEE);

        Employee managerEngland = new Employee("managerEng",
                "19840309****",
                Location.ENGLAND,
                100,
                new Date(),
                EmployePosition.MANAGER);
        Employee corpEngland = new Employee("corpEng",
                "19840309****",
                Location.ENGLAND,
                100,
                new Date(),
                EmployePosition.MANAGER);

        Employee employeeSwe = new Employee("employeeSwe",
                "19840309****",
                Location.SWEDEN,
                100,
                new Date(),
                EmployePosition.EMPLOYEE);

        Employee managerSwe = new Employee("managerSwe",
                "19840309****",
                Location.SWEDEN,
                100,
                new Date(),
                EmployePosition.MANAGER);
        Employee corpSwe = new Employee("corpSwe",
                "19840309****",
                Location.SWEDEN,
                100,
                new Date(),
                EmployePosition.MANAGER);

        database.addEmployee(employeeEngland);
        database.addEmployee(managerEngland);
        database.addEmployee(corpEngland);
        database.addEmployee(employeeSwe);
        database.addEmployee(managerSwe);
        database.addEmployee(corpSwe);
    }

    public boolean getEmployeeDiscount() {
        return employeeDiscount;
    }

    public boolean login(String userName, String password) {
       employee = new Employee();
        // employee = database.login(employeeName,password);
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

    /**DONE
     *
     * @return
     */
    public LinkedList<Product> getAvailableProducts() {
        return database.getAvailableProducts();
    }

    /**
     *
     * @param productsInOrder :
     * @param barcode : String
     */
    public boolean registerOrder(List<Product> productsInOrder, String barcode) {

        //TODO check class viariable employeeDiscount and this variable to order aswell, want to show in orderslistings

        return true;
    }

    public boolean registerNewCustomer(String name, String id, String occupation, String address) {
        return true;
    }

    public List<Employee> getEmployeesByDate(String dateFrom, String dateTo) {
        List<Employee> employees = new LinkedList<Employee>();

        employees.add(new Employee("Patrik Lind","840309****",Common.getCurrentLocation(),90,new Date(),EmployePosition.CORPORATE_SALES));
        employees.add(new Employee("Patrik Lind","840309****",Common.getCurrentLocation(),100,new Date(),EmployePosition.EMPLOYEE));
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());
        return employees;
    }

    public Employee getEmployeeByName(String employeeName) {
        return new Employee();
    }

    public boolean registerNewEmployee(String name, String id, int servieGrade, String startDate, String endDate, EmployePosition position) {
        return false;
    }

    public Employee updateEmployee(Employee employee) {
        return employee;
    }

    //TODO return -1 for error writing to db, 0 for too long comment (300charmax), 1 for ok
    public int writeComment(Employee employee, String comment) {
        if(comment.length()>=300)
            return 0;
        return 1;
    }

    public List<Flavour> getAvailableFlavours() {
        return database.getAvailableFlavours();
    }

    public void swithEmployeeDiscount() {
        employeeDiscount = !employeeDiscount;
    }

    public double calculateOrderSum(List<Product> order) {
        double sum =0;
        for(Product p : order)
            sum+=p.getPrice();

        if(employeeDiscount)
            sum = sum*0.9;
        return sum;
    }

    public void setEmployeeDiscount(boolean b) {
        employeeDiscount=b;
    }

    public List<Customer> getCustomersByDate(String dateFrom, String dateTo) {
        List<Customer> customers = new LinkedList<Customer>();
        customers.add(new Customer());
        customers.add(new Customer());
        customers.add(new Customer());
        return customers;
    }

    public Customer getCustomerByName(String customerName) {
        return new Customer();
    }

    public Customer updateCustomer(Customer customer) {
        return customer;
    }

    public List<Employee> getEmployeesByDateAndLocation(String dateFrom, String dateTo, Location location) {
        List<Employee> employees = new LinkedList<Employee>();

        employees.add(new Employee("Patrik Lind","840309****",Common.getCurrentLocation(),90,new Date(),EmployePosition.CORPORATE_SALES));
        employees.add(new Employee("Patrik Lind","840309****",Common.getCurrentLocation(),100,new Date(),EmployePosition.EMPLOYEE));
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());
        return employees;
    }

    public List<Customer> getCustomersByDateAndLocation(String dateFrom, String dateTo, Location locationToList) {
        List<Customer> customers = new LinkedList<Customer>();
        customers.add(new Customer());
        customers.add(new Customer());
        customers.add(new Customer());
        return customers;
    }

    //TODO slå samman alla av samma produkter till en, listan som ska returneras ska innehålla UNIKA produkter, på volume ska antal försäljningar av den unika produkten summeras, på pris totalsumman av försäljningarna
    public List<Product> getSalesOverTimePeriod(String dateFrom, String dateTo, Location location) {
        LinkedList<Product> products = new LinkedList<Product>();
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        //  products.add(new Product(1,"Coffe","l","roasted",39.90, 50));
        // products.add(new Product(2,"Tea","l","herbal",29.90, 50));
        //products.add(new Product(3,"Latte","l","vanilla",49.90, 50));
        //products.add(new Product(4,"Irish Cream","l","cognac",39.90, 50));
        return products;
    }

    //TODO samma som ovan, men nu ska man returnera bara den produkt som anges som inputparameter
    public List<Product> getSalesOverTimePeriodAndProduct(String date, String date1, Location location, Product product) {
        LinkedList<Product> products = new LinkedList<Product>();
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        //  products.add(new Product(1,"Coffe","l","roasted",39.90, 50));
        // products.add(new Product(2,"Tea","l","herbal",29.90, 50));
        //products.add(new Product(3,"Latte","l","vanilla",49.90, 50));
        //products.add(new Product(4,"Irish Cream","l","cognac",39.90, 50));
        return products;
    }

    public List<Product> getSalesPerCustomerZipCode(String zip, Location location) {
        LinkedList<Product> products = new LinkedList<Product>();
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        //  products.add(new Product(1,"Coffe","l","roasted",39.90, 50));
        // products.add(new Product(2,"Tea","l","herbal",29.90, 50));
        //products.add(new Product(3,"Latte","l","vanilla",49.90, 50));
        //products.add(new Product(4,"Irish Cream","l","cognac",39.90, 50));
        return products;
    }

    public List<Product> getSalesPerCustomerOccupation(String occupation, Location location) {
        LinkedList<Product> products = new LinkedList<Product>();
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        //  products.add(new Product(1,"Coffe","l","roasted",39.90, 50));
        // products.add(new Product(2,"Tea","l","herbal",29.90, 50));
        //products.add(new Product(3,"Latte","l","vanilla",49.90, 50));
        //products.add(new Product(4,"Irish Cream","l","cognac",39.90, 50));
        return products;
    }

    public List<Order> getOrdersMadeByEmployee(Employee employee, String dateFrom, String dateTo, Location location) {
        List<Order> orders = new LinkedList<Order>();
//
//        //fetch all orders made by employee on dates
//        Order order = new Order(1345,"12345654",123,Common.formatDate(dateFrom),Common.getCurrentLocation());
//        //for each order fetch all products in order
//        //calculate sum for order, get orderdate, ecustomerbarcode
//        List<Product> productsInOrder = getProductsInOrder(order);
//        order.setSum(calculateOrderSum(productsInOrder));
//        orders.add(order);
//        orders.add(order);
//        orders.add(order);
//
        return orders;
    }

    private List<Product> getProductsInOrder(Order order) {
        LinkedList<Product> products = new LinkedList<Product>();
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        products.add(new Product("Kaffe","Coffe",25.50,10.99,6.99,"l",5));
        //  products.add(new Product(1,"Coffe","l","roasted",39.90, 50));
        // products.add(new Product(2,"Tea","l","herbal",29.90, 50));
        //products.add(new Product(3,"Latte","l","vanilla",49.90, 50));
        //products.add(new Product(4,"Irish Cream","l","cognac",39.90, 50));
        return products;
    }

    public HashMap<Product, Integer> getProductsInStock(Location location, String dateFrom, String dateTo) {
        LinkedList<Product> availableProducts= getAvailableProducts();
        HashMap<Product, Integer> productQuantities = new HashMap<Product, Integer>();
        Date dateFrom1 = Common.formatDate(dateFrom);
        Date dateTo1 = Common.formatDate(dateTo);
        for(Product p : availableProducts){
            int stockQuantity = 1; //db.getStockQuantity(p, location,dateFrom1, dateTo1);
            productQuantities.put(p,stockQuantity);
        }

        return  productQuantities;
    }

    public boolean updateQuantityForProduct(Product chosenProduct, int quantityNew)
    {
        return true;
    }
}
