package userInterface;

import domainEntities.Employee;
import domainEntities.Product;
import engine.Controller;
import domainEntities.EmployePosition;

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

    /**
     * TODO mask password
     * @param choices
     */
    private void printChoices(List<String> choices) {
        for (String s : choices){
            System.out.println(s);
        }
    }

    private void loginMenu() {
        clearScreen();
        printHeader(LocalisationStrings.headerLogin());
        String userName, password;
        Scanner sc = new Scanner(System.in);
        System.out.println(LocalisationStrings.employeeName());
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
        choices.add(choices.size()+1+LocalisationStrings.placeNewOrder());
        choices.add(choices.size()+1+LocalisationStrings.registerNewCustomer());
        if(controller.getCurrentUserPosition()!=EmployePosition.EMPLOYEE){
            choices.add(choices.size()+1+LocalisationStrings.employees());
            choices.add(choices.size()+1+LocalisationStrings.customers());
            choices.add(choices.size()+1+LocalisationStrings.stock());
        }
        choices.add("0"+LocalisationStrings.logout());
        printChoices(choices);
        int choice = getInput(choices.size());
        EmployePosition position = controller.getCurrentUserPosition();

        switch (choice){
            case 6:
                System.out.println(LocalisationStrings.wrongChoice());
            case -1:
                showMainMenu();
                break;
            case 1:
                order = new LinkedList<Product>();
                newOrderMenu();
                break;
            case 2:
                registerCustomerMenu();
                break;
            case 3:
                if(position!=EmployePosition.EMPLOYEE)
                    employeeMenu();
                break;
            case 4:
                if(position!=EmployePosition.EMPLOYEE){
                    clearScreen();
                    customerMenu();
                }
                break;
            case 5:
                if(position!=EmployePosition.EMPLOYEE)
                    stockMenu();
                break;
            case 0:
                controller.logout();
                showWelcomeScreen();
                welcomeMenu();
                break;

        }
    }

    private void customerMenu() {

        printHeader(LocalisationStrings.headerCustomer());

    }

    private void stockMenu() {

        printHeader(LocalisationStrings.headerStock());
    }

    private void employeeMenu() {

        printHeader(LocalisationStrings.headerEmployee());
        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.listEmployyesByTime());
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
                System.out.println(LocalisationStrings.startDate());
                String dateFrom = sc.nextLine();
                System.out.println(LocalisationStrings.endDate());
                String dateTo = sc.nextLine();
                List<Employee> employees = controller.getEmployeesByDate(dateFrom,dateTo);
                if(employees!=null || employees.isEmpty()) {
                    TableCreator.listEmployees(employees);
                    employeeMenu();
                }else{
                    System.out.println(LocalisationStrings.empltyEmployeeList());
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
                    System.out.println(LocalisationStrings.cantFindEmployee(employeeName));
                }
                employeeMenu();
                break;
            case 3:
                showMainMenu();
                break;
        }
    }

    private void editEmployeeMenu(Employee employee) {
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
        TableCreator.showProductsTable(products);
        int amountOfChoices = products.size()+1;
        List<String> choices = new LinkedList<String>();
        choices.add(amountOfChoices+++" - "+LocalisationStrings.proceed());
        choices.add(amountOfChoices+" - "+LocalisationStrings.cancel());
        printChoices(choices);

        System.out.println("=====> "+LocalisationStrings.productsInOrder()+" <=====");
        TableCreator.showProductsTable(order);


        int choice = getInput(amountOfChoices);

        switch (choice){
            case 0:
            case -1:
                newOrderMenu();
                break;
            case 1:
                if(!products.isEmpty()){
                    order.add(products.get(choice-1));
                }
                newOrderMenu();
                break;
            case 2:
                order.add(products.get(choice-1));
                newOrderMenu();
                break;
            case 3:
                order.add(products.get(choice-1));
                newOrderMenu();
                break;
            case 4:
                order.add(products.get(choice-1));
                newOrderMenu();
                break;
            case 5:
                if(products.isEmpty()){
                    System.out.println(LocalisationStrings.noProductsSelected());
                    newOrderMenu();
                }
                checkIfRegisteredUserAndProceedWithOrder();
                order.clear();
                showMainMenu();
                break;
            case 6:
                order.clear();
                showMainMenu();
                break;
        }

    }

    private void checkIfRegisteredUserAndProceedWithOrder() {
        clearScreen();
        System.out.println(LocalisationStrings.enterUserBarcode());
        Scanner sc = new Scanner(System.in);
        String barcode = sc.nextLine();
        controller.registerOrder(order,barcode);
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
