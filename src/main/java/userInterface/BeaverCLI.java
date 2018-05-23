package userInterface;

import domainEntities.*;
import engine.Common;
import engine.Controller;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BeaverCLI {

    private Controller controller;
    private List<Product> order;


    public BeaverCLI(Controller controller){
        this.controller= controller;
        showWelcomeScreen();
        welcomeMenu();
    }

    private void welcomeMenu() {
        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.login());
        choices.add("2 - "+LocalisationStrings.exit());
        printChoices(choices);
        int choice = getInput(choices.size());
        switch (choice){
            case 0:
            case -1:
                welcomeMenu();
                break;
            case 1:
                loginMenu();
                break;
            case 2:
                System.exit(1);
                break;
        }
    }

    private void printChoices(List<String> choices) {
        for (String s : choices){
            System.out.println(s);
        }
    }

    //TODO MASK PASSWORD
    private void loginMenu() {
        clearScreen();
        printHeader(LocalisationStrings.headerLogin());
        String userName, password;
        Scanner sc = new Scanner(System.in);
        System.out.println(LocalisationStrings.name());
        userName = sc.nextLine();
        System.out.println(LocalisationStrings.password());
        password = sc.nextLine();

        boolean loginOk = controller.login(userName, password);
        if(loginOk)
            showMainMenu();
        else{
            System.out.println(LocalisationStrings.incorrectCredentials());
            String input = sc.nextLine();
            if(input.equalsIgnoreCase("y"))
                loginMenu();
            else
                System.exit(1);

        }
    }

    private void showMainMenu() {
        clearScreen();
        printHeader(LocalisationStrings.headerMainMenu());
        List<String> choices = new LinkedList<String>();
        choices.add(choices.size()+LocalisationStrings.logout());
        choices.add(choices.size()+LocalisationStrings.placeNewOrder());
        choices.add(choices.size()+LocalisationStrings.registerNewCustomer());
        if(controller.getCurrentUserPosition()!=EmployePosition.EMPLOYEE){
            choices.add(choices.size()+LocalisationStrings.employees());
            choices.add(choices.size()+LocalisationStrings.customers());
            choices.add(choices.size()+LocalisationStrings.stock());
            choices.add(choices.size()+" - "+LocalisationStrings.report());
        }
        printChoices(choices);
        int choice = getInput(choices.size());
        EmployePosition position = controller.getCurrentUserPosition();

        switch (choice){

            case 7:
                System.out.println(LocalisationStrings.wrongChoice());
            case -1:
                showMainMenu();
                break;
            case 1:
                order = new LinkedList<Product>();
                controller.setEmployeeDiscount(false);
                newOrderMenu();
                break;
            case 2:
                registerCustomerMenu();
                break;
            case 3:
                employeeMenu();
                break;
            case 4:
                clearScreen();
                customerMenu();
                break;
            case 5:
                stockMenu();
                break;
            case 6:
                reportMenu();
            case 0:
                controller.logout();
                showWelcomeScreen();
                welcomeMenu();
                break;

        }
    }

    private void reportMenu() {
        printHeader(LocalisationStrings.report());
        List<String> choices = new LinkedList<String>();
        choices.add(choices.size()+1+" - "+LocalisationStrings.salesPerTimePeriod());
        choices.add(choices.size()+1+" - "+LocalisationStrings.salesPerTimePeriodPerProducts());
        choices.add(choices.size()+1+" - "+LocalisationStrings.salesPerCustomerZipCode());
        choices.add(choices.size()+1+" - "+LocalisationStrings.salesPerCustomerOccupation());
        choices.add(choices.size()+1+" - "+LocalisationStrings.cancel());

        printChoices(choices);
        int choice = getInput(choices.size());

        Scanner sc = new Scanner(System.in);
        String[] dates;
        EmployePosition position = controller.getCurrentUserPosition();
        Location location = Common.getCurrentLocation();
        List<Product> products;
        double sum;

        switch (choice){
            case 0:
            case -1:
                System.out.println(LocalisationStrings.wrongChoice());
                reportMenu();
                break;
            case 1:
                dates = inputDates();
                if(position==EmployePosition.CORPORATE_SALES){
                    location = selectLocation();
                }
                products = controller.getSalesOverTimePeriod(dates[0],dates[1],location);
                if(products==null || products.isEmpty()){
                    System.out.println(LocalisationStrings.someThingWrong());
                    reportMenu();
                }
                sum = controller.calculateOrderSum(products);
                TableCreator.listProductSales(products,location,dates[0],dates[1],sum);
                reportMenu();
                break;
            case 2:
                dates = inputDates();
                if(position==EmployePosition.CORPORATE_SALES){
                    location = selectLocation();
                }
                List<Product> availableProducts = controller.getAvailableProducts();
                TableCreator.showProductsTable(availableProducts);
                int chosenProduct = getInput(availableProducts.size());
                if(chosenProduct==-1){
                    System.out.println(LocalisationStrings.wrongChoice());
                    reportMenu();
                }
                Product product = availableProducts.get(chosenProduct);
                products = controller.getSalesOverTimePeriodAndProduct(dates[0],dates[1],location,product);
                if(products==null || products.isEmpty()){
                    System.out.println(LocalisationStrings.someThingWrong());
                    reportMenu();
                }
                sum = controller.calculateOrderSum(products);
                TableCreator.listProductSales(products,location,dates[0],dates[1],sum);
                reportMenu();
                break;
            case 3:
                System.out.println(LocalisationStrings.zipcode()+": ");
                String zip = sc.nextLine();
                if(position==EmployePosition.CORPORATE_SALES){
                    location = selectLocation();
                }
                products = controller.getSalesPerCustomerZipCode(zip,location);
                if(products==null || products.isEmpty()){
                    System.out.println(LocalisationStrings.someThingWrong());
                    reportMenu();
                }
                sum = controller.calculateOrderSum(products);
                zip+=","+LocalisationStrings.zipcode();
                TableCreator.listProductSalesZipOrOccupation(products,location,zip,sum);
                reportMenu();
                break;

            case 4:
                System.out.println(LocalisationStrings.occupation()+": ");
                String occupation = sc.nextLine();
                if(position==EmployePosition.CORPORATE_SALES){
                    location = selectLocation();
                }
                products = controller.getSalesPerCustomerOccupation(occupation,location);
                if(products==null || products.isEmpty()){
                    System.out.println(LocalisationStrings.someThingWrong());
                    reportMenu();
                }
                sum = controller.calculateOrderSum(products);
                occupation+=","+LocalisationStrings.occupation();
                TableCreator.listProductSalesZipOrOccupation(products,location,occupation,sum);
                reportMenu();
                break;
            case 5:
                showMainMenu();
                break;
        }
    }

    private String[] inputDates() {
        String[] dates = new String[2];
        Scanner sc = new Scanner(System.in);
        System.out.println(LocalisationStrings.inputStartDate());
        dates[0] = sc.nextLine();
        System.out.println(LocalisationStrings.inputEndDate());
        dates[1] = sc.nextLine();
        return dates;
    }

    private void customerMenu() {

        printHeader(LocalisationStrings.headerCustomer());

        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.listCustomersByTime());
        choices.add("2 - "+LocalisationStrings.searchByName());
        choices.add("3 - "+LocalisationStrings.cancel());
        printChoices(choices);
        int choice = getInput(choices.size());

        Scanner sc = new Scanner (System.in);
        switch (choice){
            case 0:
            case -1:
                customerMenu();
                break;
            case 1:
                System.out.println(LocalisationStrings.inputStartDate());
                String dateFrom = sc.nextLine();
                System.out.println(LocalisationStrings.inputEndDate());
                String dateTo = sc.nextLine();
                Location locationToList = Common.getCurrentLocation();
                EmployePosition position = controller.getCurrentUserPosition();
                List<Customer> customers = null;

                if(position==EmployePosition.CORPORATE_SALES){
                    locationToList = selectLocation();
                    customers = controller.getCustomersByDateAndLocation(dateFrom,dateTo,locationToList);
                }
                if(position==EmployePosition.MANAGER){
                    customers= controller.getCustomersByDate(dateFrom,dateTo);
                }

                if(customers!=null || !customers.isEmpty()) {
                    TableCreator.listCustomers(customers,locationToList,dateFrom,dateTo);
                    customerMenu();
                }else{
                    System.out.println(LocalisationStrings.emptyList());
                    customerMenu();
                }
                break;
            case 2:
                System.out.println(LocalisationStrings.customerName());
                String customerName = sc.nextLine();
                Customer customer = controller.getCustomerByName(customerName);
                if(customer!=null){
                    editCustomerMenu(customer);
                }else{
                    System.out.println(LocalisationStrings.cantFindPerson(customerName));
                }
                customerMenu();
                break;
            case 3:
                showMainMenu();
                break;
        }

    }

    private Location selectLocation() {
        List<String> choicesLocation = new LinkedList<String>();
        Location[] locations = Location.values();
        for(int i = 0; i<locations.length;i++){
            choicesLocation.add((i+1)+" - "+locations[i].name());
        }
        printChoices(choicesLocation);
        int chosenLocation = getInput(choicesLocation.size());
        if(chosenLocation<1 || chosenLocation>locations.length){
            System.out.println(LocalisationStrings.wrongChoice());
            employeeMenu();
        }
        return locations[chosenLocation-1];
    }

    private void editCustomerMenu(Customer customer) {

        clearScreen();
        printHeader(LocalisationStrings.headerEditCustomer());

        //name, id, occupation, barcode, address, regDate, counter
        System.out.println(LocalisationStrings.name()+": "+customer.getName());
        System.out.println(LocalisationStrings.id()+": "+customer.getIdNumber());
        System.out.println(LocalisationStrings.registeredDate()+": "+customer.getRegisteredDate());
        System.out.println(LocalisationStrings.address()+": "+customer.getAddress());
        System.out.println(LocalisationStrings.occupation()+": "+customer.getOccupation());
        System.out.println(LocalisationStrings.barcode()+": "+customer.getBarcode());
        System.out.println(LocalisationStrings.amountOfPurchases()+": "+customer.getTotalPurchases());
        System.out.println();

        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.edit()+" "+LocalisationStrings.name());
        choices.add("2 - "+LocalisationStrings.edit()+" "+LocalisationStrings.id());
        choices.add("3 - "+LocalisationStrings.edit()+" "+LocalisationStrings.registeredDate());
        choices.add("4 - "+LocalisationStrings.edit()+" "+LocalisationStrings.address());
        choices.add("5 - "+LocalisationStrings.edit()+" "+LocalisationStrings.occupation());
        choices.add("6 - "+LocalisationStrings.edit()+" "+LocalisationStrings.barcode());
        choices.add("7 - "+LocalisationStrings.cancel());

        printChoices(choices);
        int choice = getInput(choices.size());

        Scanner sc = new Scanner(System.in);
        Customer customerUpdated=null;
        switch (choice){
            case 0:
            case -1:
                System.out.println(LocalisationStrings.wrongChoice());
                editCustomerMenu(customer);
                break;
            case 1:
                System.out.println(LocalisationStrings.name()+": ");
                String name = sc.nextLine();
                customer.setName(name);
                customerUpdated = controller.updateCustomer(customer);
                if(customerUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editCustomerMenu(customer);
                }
                editCustomerMenu(customerUpdated);
                break;
            case 2:
                System.out.println(LocalisationStrings.id()+": ");
                String id = sc.nextLine();
                customer.setIdNumber(id);
                customerUpdated = controller.updateCustomer(customer);
                if(customerUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editCustomerMenu(customer);
                }
                editCustomerMenu(customerUpdated);
                break;
            case 3:
                System.out.println(LocalisationStrings.inputStartDate()+": ");
                String startDate = sc.nextLine();
                customer.setRegisteredDate(startDate);
                customerUpdated = controller.updateCustomer(customer);
                if(customerUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editCustomerMenu(customer);
                }
                editCustomerMenu(customerUpdated);
                break;
            case 4:
                System.out.println(LocalisationStrings.address()+": ");
                String address = sc.nextLine();
                String[] addressVals = address.split(",");
                customer.setAddress(new Address(addressVals[0],addressVals[1],addressVals[2], Common.getCurrentLocation()));
                customerUpdated = controller.updateCustomer(customer);
                if(customerUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editCustomerMenu(customer);
                }
                editCustomerMenu(customerUpdated);
                break;
            case 5:
                System.out.println(LocalisationStrings.occupation()+": ");
                String occupation = sc.nextLine();
                customer.setOccupation(occupation);
                customerUpdated = controller.updateCustomer(customer);
                if(customerUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editCustomerMenu(customer);
                }
                editCustomerMenu(customerUpdated);
                break;
            case 6:
                System.out.println(LocalisationStrings.barcode()+": ");
                String barcode = sc.nextLine();
                customer.setBarcode(barcode);
                customerUpdated = controller.updateCustomer(customer);
                if(customerUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editCustomerMenu(customer);
                }
                editCustomerMenu(customerUpdated);
                break;
            case 7:
                employeeMenu();
                break;

        }

    }

    private void stockMenu() {

        printHeader(LocalisationStrings.headerStock());
    }

    private void employeeMenu() {

        printHeader(LocalisationStrings.headerEmployee());
        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.listEmployyesByTime());
        choices.add("2 - "+LocalisationStrings.searchByName());
        choices.add("3 - "+LocalisationStrings.createEmployee());
        choices.add("4 - "+LocalisationStrings.cancel());
        printChoices(choices);
        int choice = getInput(choices.size());

        Scanner sc = new Scanner (System.in);
        switch (choice){
            case 0:
            case -1:
                customerMenu();
                break;
            case 1:
                System.out.println(LocalisationStrings.inputStartDate());
                String dateFrom = sc.nextLine();
                System.out.println(LocalisationStrings.inputEndDate());
                String dateTo = sc.nextLine();

                Location locationToList = Common.getCurrentLocation();
                EmployePosition position = controller.getCurrentUserPosition();
                List<Employee> employees=null;

                if(position==EmployePosition.CORPORATE_SALES){
                    locationToList = selectLocation();
                    employees = controller.getEmployeesByDateAndLocation(dateFrom,dateTo,locationToList);
                }
                if(position==EmployePosition.MANAGER){
                    employees= controller.getEmployeesByDate(dateFrom,dateTo);
                }

                if(employees!=null || !employees.isEmpty()) {
                    TableCreator.listEmployees(employees, locationToList, dateFrom,dateTo );
                    employeeMenu();
                }else{
                    System.out.println(LocalisationStrings.emptyList());
                    employeeMenu();
                }
                break;
            case 2:
                System.out.println(LocalisationStrings.employeeName());
                String employeeName = sc.nextLine();
                Employee employee = controller.getEmployeeByName(employeeName);
                if(employee!=null){
                    editEmployeeMenu(employee);
                }else{
                    System.out.println(LocalisationStrings.cantFindPerson(employeeName));
                }
                employeeMenu();
                break;
            case 3:
                showRegisterNewEmployeeMenu();
                employeeMenu();
                break;
            case 4:
                showMainMenu();
                break;
        }
    }

    private void showRegisterNewEmployeeMenu() {
        clearScreen();
        Scanner sc = new Scanner(System.in);
        System.out.println(LocalisationStrings.name()+":");
        String name = sc.nextLine();
        System.out.println(LocalisationStrings.id()+": ");
        String id = sc.nextLine();
        System.out.println(LocalisationStrings.serviceGrade()+": ");
        int servieGrade=1;
        try{
            servieGrade = sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println(LocalisationStrings.inputMismatch());
            showRegisterNewEmployeeMenu();
        }
        System.out.println(LocalisationStrings.inputStartDate()+": ");
        sc = new Scanner(System.in);
        String startDate = sc.nextLine();
        System.out.println(LocalisationStrings.inputEndDate()+": ");
        String endDate = sc.nextLine();

        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.positionEmployee());
        choices.add("2 - "+LocalisationStrings.positionManager());
        choices.add("3 - "+LocalisationStrings.positionCorporate());
        printChoices(choices);

        EmployePosition position = null;
        int choice = getInput(choices.size());
        switch (choice){
            case 0:
            case -1:
                System.out.println(LocalisationStrings.wrongChoice());
                showRegisterNewEmployeeMenu();
                break;
            case 1:
                position = EmployePosition.EMPLOYEE;
                break;
            case 2:
                position = EmployePosition.MANAGER;
                break;
            case 3:
                position = EmployePosition.CORPORATE_SALES;
                break;
        }

        boolean registerOk = controller.registerNewEmployee(name, id,servieGrade,startDate,endDate,position);
        if(registerOk){
            System.out.println("OK");
            employeeMenu();
        }else{
            System.out.println(LocalisationStrings.someThingWrong());
            employeeMenu();
        }
    }

    private void editEmployeeMenu(Employee employee) {
        clearScreen();
        printHeader(LocalisationStrings.headerEditEmployee());
        System.out.println(LocalisationStrings.name()+": "+employee.getName());
        System.out.println(LocalisationStrings.id()+": "+employee.getIdNumber());
        System.out.println(LocalisationStrings.startDate()+": "+employee.getStartDate());
        System.out.println(LocalisationStrings.endDate()+": "+employee.getEndDate());
        String serviceGrade = Integer.toString(employee.getServiceGrade())+"%";
        System.out.println(LocalisationStrings.serviceGrade()+": "+serviceGrade);
        System.out.println(LocalisationStrings.position()+": "+employee.getPosition());
        System.out.println();

        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.edit()+" "+LocalisationStrings.name());
        choices.add("2 - "+LocalisationStrings.edit()+" "+LocalisationStrings.id());
        choices.add("3 - "+LocalisationStrings.edit()+" "+LocalisationStrings.startDate());
        choices.add("4 - "+LocalisationStrings.edit()+" "+LocalisationStrings.endDate());
        choices.add("5 - "+LocalisationStrings.edit()+" "+LocalisationStrings.serviceGrade());
        choices.add("6 - "+LocalisationStrings.edit()+" "+LocalisationStrings.position());
        choices.add("7 - "+LocalisationStrings.orderHistory());
        choices.add("8 - "+LocalisationStrings.showComments());
        choices.add("9 - "+LocalisationStrings.writeComment());
        choices.add("10 - "+LocalisationStrings.cancel());
        printChoices(choices);

        int choice = getInput(choices.size());
        Scanner sc = new Scanner(System.in);
        Employee employeeUpdated =null;
        switch (choice){
            case 0:
            case -1:
                System.out.println(LocalisationStrings.wrongChoice());
                editEmployeeMenu(employee);
                break;
            case 1:
                System.out.println(LocalisationStrings.name()+": ");
                String name = sc.nextLine();
                employee.setName(name);
                employeeUpdated = controller.updateEmployee(employee);
                if(employeeUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editEmployeeMenu(employee);
                }
                editEmployeeMenu(employeeUpdated);
                break;
            case 2:
                System.out.println(LocalisationStrings.id()+": ");
                String id = sc.nextLine();
                employee.setIdNumber(id);
                employeeUpdated = controller.updateEmployee(employee);
                if(employeeUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editEmployeeMenu(employee);
                }
                editEmployeeMenu(employeeUpdated);
                break;
            case 3:
                System.out.println(LocalisationStrings.inputStartDate()+": ");
                String startDate = sc.nextLine();
                employee.setStartDate(startDate);
                employeeUpdated = controller.updateEmployee(employee);
                if(employeeUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editEmployeeMenu(employee);
                }
                editEmployeeMenu(employeeUpdated);
                break;
            case 4:
                System.out.println(LocalisationStrings.inputEndDate()+": ");
                String endDate = sc.nextLine();
                employee.setEndDate(endDate);
                employeeUpdated = controller.updateEmployee(employee);
                if(employeeUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editEmployeeMenu(employee);
                }
                editEmployeeMenu(employeeUpdated);
                break;
            case 5:
                System.out.println(LocalisationStrings.serviceGrade()+"(0-100): ");
                int serviceGrade2=employee.getServiceGrade();
                try{
                    serviceGrade2= sc.nextInt();
                    if(serviceGrade2<0 || serviceGrade2>100){
                        System.out.println(LocalisationStrings.wrongChoice()+", "+LocalisationStrings.serviceGrade()+": "+serviceGrade2);
                        editEmployeeMenu(employee);
                    }
                    employee.setServiceGrade(serviceGrade2);
                    employeeUpdated = controller.updateEmployee(employee);
                    editEmployeeMenu(employeeUpdated);

                }catch (InputMismatchException e){
                    System.out.println(LocalisationStrings.inputMismatch());
                    editEmployeeMenu(employee);
                }
                break;
            case 6:
                System.out.println(LocalisationStrings.position()+": ");
                List choicesPosition = new LinkedList();
                choicesPosition.add("1 - "+LocalisationStrings.positionEmployee());
                choicesPosition.add("2 - "+LocalisationStrings.positionManager());
                choicesPosition.add("3 - "+LocalisationStrings.positionCorporate());
                printChoices(choicesPosition);
                int positionChoice = getInput(choicesPosition.size());
                EmployePosition position = employee.getPosition();
                if(positionChoice ==0 || positionChoice ==-1){
                    System.out.println(LocalisationStrings.wrongChoice());
                    editEmployeeMenu(employee);
                }
                if(positionChoice==1){
                    position = EmployePosition.EMPLOYEE;
                }
                if(positionChoice==2){
                    position = EmployePosition.MANAGER;
                }
                if(positionChoice==3){
                    position = EmployePosition.CORPORATE_SALES;
                }
                employee.setPosition(position);
                employeeUpdated = controller.updateEmployee(employee);
                if(employeeUpdated==null){
                    System.out.println(LocalisationStrings.someThingWrong());
                    editEmployeeMenu(employee);
                }
                editEmployeeMenu(employeeUpdated);
                break;
            case 7:
                showOrderHistoryMenu(employee);
                editEmployeeMenu(employee);
                break;
            case 8:
                showComments(employee);
                editEmployeeMenu(employee);
                break;
            case 9:
                writeComment(employee);
                editEmployeeMenu(employee);
                break;
            case 10:
                employeeMenu();
                break;

        }
    }

    private void showOrderHistoryMenu(Employee employee) {
        printHeader(LocalisationStrings.orderHistory());
        String[] dates = inputDates();
        Location location = Common.getCurrentLocation();
        if(controller.getCurrentUserPosition()==EmployePosition.CORPORATE_SALES){
            location = selectLocation();
        }
        List<Order> ordersByEmployee = controller.getOrdersMadeByEmployee(employee,dates[0],dates[1],location);
        TableCreator.listOrdersByEmployee(employee,ordersByEmployee,location);
        editEmployeeMenu(employee);
    }

    private void writeComment(Employee employee) {
        printHeader(LocalisationStrings.writeComment()+" - "+employee.getName());
        Scanner sc = new Scanner(System.in);
        String comment = sc.nextLine();
        int returnCode = controller.writeComment(employee,comment);
        switch (returnCode){
            case -1:
                System.out.println(LocalisationStrings.someThingWrong());
                writeComment(employee);
                break;
            case 0:
                System.out.println(LocalisationStrings.commentTooBig());
                writeComment(employee);
                break;
            case 1:
                System.out.println("OK");
                editEmployeeMenu(controller.getEmployeeByName(employee.getName()));
                break;
        }

    }

    private void showComments(Employee employee) {
        clearScreen();
        printHeader(employee.getName()+" "+LocalisationStrings.comment());
        List<Comment> comments = employee.getComments();
        String leftAlignFormat = "%-20s, %-20s: %-310s %n";

        for(Comment comment : comments){
            System.out.format(leftAlignFormat,comment.getDate(),comment.getEmployerId(),comment.getComment());
        }
    }

    private void registerCustomerMenu() {
        printHeader(LocalisationStrings.headerRegisterCustomer());
        Scanner sc = new Scanner(System.in);
        System.out.println(LocalisationStrings.customerName());
        String name = sc.nextLine();
        System.out.println(LocalisationStrings.customerId());
        String custId = sc.nextLine();
        System.out.println(LocalisationStrings.occupation());
        String occupation = sc.nextLine();
        System.out.println(LocalisationStrings.address());
        String address = sc.nextLine();

        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.proceed());
        choices.add("2 - "+LocalisationStrings.cancel());
        printChoices(choices);
        int choice = getInput(choices.size());

        switch (choice){
            case 0:
            case -1:
                registerCustomerMenu();
                break;
            case 1:
                boolean registerOk =  controller.registerNewCustomer(name,custId,occupation,address);
                if(!registerOk)
                    System.out.println(LocalisationStrings.someThingWrong());
                showMainMenu();
                break;
            case 2:
                showMainMenu();
                break;
        }

    }


    private void newOrderMenu() {

        clearScreen();
        printHeader(LocalisationStrings.headerCreateOrder());
        List<Product> products = controller.getAvailableProducts();

        if(products.isEmpty()){
            System.out.println(LocalisationStrings.someThingWrong());
            showMainMenu();
        }

        System.out.println(LocalisationStrings.employeeDiscount()+": "+controller.getEmployeeDiscount());
        TableCreator.showProductsTable(products);
        int amountOfChoices = products.size()+1;
        List<String> choices = new LinkedList<String>();
        choices.add(amountOfChoices+++" - "+LocalisationStrings.employeeDiscount());
        choices.add(amountOfChoices+++" - "+LocalisationStrings.remove()+" "+LocalisationStrings.product());
        choices.add(amountOfChoices+++" - "+LocalisationStrings.proceed());
        choices.add(amountOfChoices+" - "+LocalisationStrings.cancel());
        printChoices(choices);

        System.out.println("=====> "+LocalisationStrings.productsInOrder()+" <=====");
        double sum = controller.calculateOrderSum(order);
        TableCreator.showCurrentOrderInTable(order, sum);


        int choice = getInput(amountOfChoices);
        Product product;
        Flavour flavour;

        //TODO check that cases match products when they are implemented
        switch (choice){
            case 0:
            case -1:
                newOrderMenu();
                break;
            case 1:
                flavour = selectFlavour();
                product = products.get(choice-1);
                product.setFlavour(flavour);
                order.add(product);
                newOrderMenu();
                break;
            case 2:
                flavour = selectFlavour();
                product = products.get(choice-1);
                product.setFlavour(flavour);
                order.add(product);
                newOrderMenu();
                break;
            case 3:
                flavour = selectFlavour();
                product = products.get(choice-1);
                product.setFlavour(flavour);
                order.add(product);
                newOrderMenu();
                break;
            case 4:
                controller.swithEmployeeDiscount();
                newOrderMenu();
                break;
            case 5:
                System.out.println(LocalisationStrings.remove()+" id: ");
                int choiceRemoveProduct;
                Scanner sc = new Scanner(System.in);
                try{
                    choiceRemoveProduct = sc.nextInt();
                    if(choiceRemoveProduct<1 || choiceRemoveProduct>order.size()){
                        System.out.println(LocalisationStrings.wrongChoice());
                        newOrderMenu();
                    }
                    order.remove(choiceRemoveProduct-1);
                    newOrderMenu();
                }catch (InputMismatchException e){
                    System.out.println(LocalisationStrings.wrongChoice());
                    newOrderMenu();
                }
                break;
            case 6:
                if(!order.isEmpty()){
                    checkIfRegisteredUserAndProceedWithOrder();
                    order.clear();
                }else{
                    System.out.println(LocalisationStrings.noProductsSelected());
                }
                showMainMenu();
                break;
            case 7:
                order.clear();
                showMainMenu();
                break;
        }

    }

    //TODO refactor switch when flavours are implemented in db
    private Flavour selectFlavour() {
        List<Flavour> flavours = controller.getAvailableFlavours();
        List<String> choices = new LinkedList<String>();
        System.out.println(LocalisationStrings.select()+" " +LocalisationStrings.flavour()+": ");
        for(int i = 0; i<flavours.size();i++){
            choices.add(i+1+" - "+flavours.get(i).getName());
        }

        printChoices(choices);
        int choice = getInput(choices.size());

        Flavour flavour=null;
        switch (choice){
            case 0:
            case -1:
                System.out.println(LocalisationStrings.wrongChoice());
                selectFlavour();
                break;
            case 1:
                flavour = flavours.get(choice-1);
                break;

            case 2:
                flavour = flavours.get(choice-1);
                break;

            case 3:
                flavour = flavours.get(choice-1);
                break;

            case 4:
                flavour = flavours.get(choice-1);
                break;

        }
        return flavour;
    }

    private void checkIfRegisteredUserAndProceedWithOrder() {
        clearScreen();
        String barcode="";
        if(!controller.getEmployeeDiscount()){
            System.out.println(LocalisationStrings.enterUserBarcode());
            Scanner sc = new Scanner(System.in);
            barcode = sc.nextLine();
        }
        boolean registerOk = controller.registerOrder(order,barcode);
        if(!registerOk)
            System.out.println(LocalisationStrings.someThingWrong());
        else
            System.out.println("OK");
    }

    private void printHeader(String header) {
        System.out.println("##### "+header+" #####");
    }

    private int getInput(int size) {
        int choice;
        Scanner sc = new Scanner(System.in);
        try{
            choice = sc.nextInt();
            if(choice<0 || choice>size){
                System.out.println(LocalisationStrings.wrongChoice());
                choice = -1;
            }
        }catch (InputMismatchException e){
            System.out.println(LocalisationStrings.inputMismatch());
            choice =-1;
        }
        return  choice;
    }

    private void showWelcomeScreen() {

        System.out.println("\n" +
                "  ____                             _____       __  __                     ____   \n" +
                " |  _ \\                           / ____|     / _|/ _|              /\\   |  _ \\  \n" +
                " | |_) | ___  __ ___   _____ _ __| |     ___ | |_| |_ ___  ___     /  \\  | |_) | \n" +
                " |  _ < / _ \\/ _` \\ \\ / / _ \\ '__| |    / _ \\|  _|  _/ _ \\/ _ \\   / /\\ \\ |  _ <  \n" +
                " | |_) |  __/ (_| |\\ V /  __/ |  | |___| (_) | | | ||  __/  __/  / ____ \\| |_) | \n" +
                " |____/ \\___|\\__,_| \\_/ \\___|_|   \\_____\\___/|_| |_| \\___|\\___| /_/    \\_\\____/  \n" +
                "                                                                                 \n" +
                "                                                                                 \n");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
