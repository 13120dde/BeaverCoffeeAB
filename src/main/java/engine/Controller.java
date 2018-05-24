package engine;

import database.MongoDb;
import domainEntities.*;
import userInterface.LocalisationStrings;

import java.util.*;

public class Controller {

    private MongoDb database;
    private Employee employee;
    private boolean employeeDiscount = false, fillDBWithProducts = true;


    public Controller(MongoDb database) {
        this.database=database;

        //Just for filling db with products for the very first time
        if(fillDBWithProducts){
            fillDbWithInitialVals();
        }
        Common.setCurrentLocation(Location.ENGLAND);
    }

    //DONE
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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        Date endDate = calendar.getTime();
        Employee employeeEngland = new Employee("employeeEng",
                "19840309****",
                Location.ENGLAND,
                100,
                Common.getCurrentDate(),
                endDate,
                EmployePosition.EMPLOYEE);

        Employee managerEngland = new Employee("managerEng",
                "19840309****",
                Location.ENGLAND,
                100,
                Common.getCurrentDate(),
                endDate,
                EmployePosition.MANAGER);
        Employee corpEngland = new Employee("corpEng",
                "19840309****",
                Location.ENGLAND,
                100,
                Common.getCurrentDate(),
                endDate,
                EmployePosition.MANAGER);

        Employee employeeSwe = new Employee("employeeSwe",
                "19840309****",
                Location.SWEDEN,
                100,
                Common.getCurrentDate(),
                endDate,

                EmployePosition.EMPLOYEE);

        Employee managerSwe = new Employee("managerSwe",
                "19840309****",
                Location.SWEDEN,
                100,
                Common.getCurrentDate(),
                endDate,

                EmployePosition.MANAGER);
        Employee corpSwe = new Employee("corpSwe",
                "19840309****",
                Location.SWEDEN,
                100,
                Common.getCurrentDate(),
                endDate,

                EmployePosition.MANAGER);

        database.addEmployee(employeeEngland);
        database.addEmployee(managerEngland);
        database.addEmployee(corpEngland);
        database.addEmployee(employeeSwe);
        database.addEmployee(managerSwe);
        database.addEmployee(corpSwe);
    }

    //DONE
    public boolean getEmployeeDiscount() {
        return employeeDiscount;
    }


    //DONE
    public boolean login(String userName, String password) {

       Employee employee = database.login(userName);
       if(employee==null){
           return false;
       }else{
           if(password.equals(employee.getPassword())){
               this.employee=employee;

               LocalisationStrings.setLocation(this.employee.getLocation());
               Common.setCurrentLocation(this.employee.getLocation());
               return true;
           }
           return false;
       }
    }

    //DONE
    public EmployePosition getCurrentUserPosition() {
        return employee.getPosition();
    }

    //DONE
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
     *  @param productsInOrder :
     * @param barcode : String
     */
    public int registerOrder(List<Product> productsInOrder, String barcode) {
        //TODO check class viariable employeeDiscount and this variable to order aswell, want to show in orderslistings

        //not a customer
        if(barcode.isEmpty()){
            Order order = new Order(barcode,employee.getObjectId(),Common.getCurrentDate(),Common.getCurrentLocation(),productsInOrder);
            double sum=0;
            for(Product p : productsInOrder){
                sum+=p.getPrice();
            }
            if(employeeDiscount)
                sum*=0.9;
            order.setSum(sum);
            database.addOrder(order);
            return -2;

            //Customer check discount
        }else{
           Customer customer = database.getCustomerByBarcode(barcode);
           int purchases, indexToDiscountedProduct=-1;
           if(customer!=null){
               purchases=customer.getTotalPurchases();
               if(purchases%10==0){
                   //get first eligible product and discount
               }else{
                   int rest = purchases%10;
                   for(int i = 0;i<productsInOrder.size();i++){
                       if(productsInOrder.get(i).isEligibleForDiscount()){
                           rest++;
                           if(rest%10==0){
                               indexToDiscountedProduct=i;
                               break;

                           }
                       }
                   }
                   if(indexToDiscountedProduct>0 && indexToDiscountedProduct<productsInOrder.size()){
                       productsInOrder.get(indexToDiscountedProduct).setPriceGBP(0);
                       productsInOrder.get(indexToDiscountedProduct).setPriceSEK(0);
                       productsInOrder.get(indexToDiscountedProduct).setPriceUSD(0);
                   }
                   Order order = new Order(barcode,employee.getObjectId(),Common.getCurrentDate(),Common.getCurrentLocation(),productsInOrder);
                   double sum=0;
                   for(Product p : productsInOrder){
                       sum+=p.getPrice();
                   }
                   if(employeeDiscount)
                       sum*=0.9;
                   order.setSum(sum);
                   database.addOrder(order);
                   return indexToDiscountedProduct;
               }
           }


        }
        return -3;
    }

    public boolean registerNewCustomer(String name, String id, String occupation, String address) {
        int barcodeSeed = new Random().nextInt(1000)+1;
        String[] initials = name.split(" ");
        String prefixBarcode ="";
        for(int i = 0;i<initials.length;i++){
            prefixBarcode+=initials[i].substring(0,1);
        }
        String barcode=prefixBarcode+Integer.toString(barcodeSeed);
        String[] addressSplit = address.split(",");
        Address address1 = new Address(addressSplit[0],addressSplit[1],addressSplit[2],Common.getCurrentLocation());
        Customer customer = new Customer(name,
                occupation,
                barcode,
                id,
                address1,
                Common.getCurrentDate());
        database.addCustomer(customer);
        return true;
    }

    public List<Employee> getEmployeesByDate(Date dateFrom, Date dateTo) {
        return database.getEmployeesByDate(dateFrom,dateTo);
    }

    public Employee getEmployeeByName(String employeeName) {
        return database.getEmployeeByName(employeeName);
    }

    public boolean registerNewEmployee(String name, String id, int servieGrade, Date startDate, Date endDate, EmployePosition position) {
        Employee employee = new Employee(name,id,Common.getCurrentLocation(),servieGrade,startDate,endDate,position);
        return database.addEmployee(employee);
    }

    public Employee updateEmployee(Employee employee) {
        boolean ok = database.updateEmployee(employee);
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

    public List<Customer>getCustomersByDate(Date dateFrom, Date dateTo) {
        return database.getCustomersByDate(dateFrom,dateTo);
    }

    public Customer getCustomerByName(String customerName) {
        return database.getCustomerByName(customerName);
    }

    public Customer updateCustomer(Customer customer) {
        boolean ok = database.updateCustomer(customer);
        return customer;
    }

    public List<Employee> getEmployeesByDateAndLocation(Date dateFrom, Date dateTo, Location location) {
        List<Employee> employees = new LinkedList<Employee>();

        return employees;
    }

    public List<Customer> getCustomersByDateAndLocation(Date dateFrom, Date dateTo, Location locationToList) {
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
