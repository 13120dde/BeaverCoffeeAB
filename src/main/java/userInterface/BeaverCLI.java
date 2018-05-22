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
                List<Employee> employees = controller.getEmployeesByDate(dateFrom,dateTo);
                if(employees!=null || !employees.isEmpty()) {
                    System.out.println("Listing all employees for dates: "+dateFrom+" - "+dateTo+"\nLocation: "+Common.getCurrentLocation()+"\n\n");
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

        List<String> choices = new LinkedList<String>();
        choices.add("1 - "+LocalisationStrings.edit()+" "+LocalisationStrings.name());
        choices.add("2 - "+LocalisationStrings.edit()+" "+LocalisationStrings.id());
        choices.add("3 - "+LocalisationStrings.edit()+" "+LocalisationStrings.startDate());
        choices.add("4 - "+LocalisationStrings.edit()+" "+LocalisationStrings.endDate());
        choices.add("5 - "+LocalisationStrings.edit()+" "+LocalisationStrings.serviceGrade());
        choices.add("6 - "+LocalisationStrings.edit()+" "+LocalisationStrings.position());
        choices.add("7 - "+LocalisationStrings.showComments());
        choices.add("8 - "+LocalisationStrings.writeComment());
        choices.add("9 - "+LocalisationStrings.cancel());
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
                showComments(employee);
                editEmployeeMenu(employee);
                break;
            case 8:
                writeComment(employee);
                editEmployeeMenu(employee);
                break;
            case 9:
                employeeMenu();
                break;

        }
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
