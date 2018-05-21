package userInterface;

import domainEntities.Product;
import engine.Controller;
import domainEntities.EmployePosition;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BeaverCLI {

    private Controller controller;

    public BeaverCLI(Controller controller){
        this.controller= controller;
        showWelcomeScreen();
        welcomeMenu();
    }

    private void welcomeMenu() {
        List<String> choices = new LinkedList<String>();
        choices.add("1 - Login");
        choices.add("2 - Exit");
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
        printHeader("LOGIN");
        String userName, password;
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        userName = sc.nextLine();
        System.out.println("Password: ");
        password = sc.nextLine();

        boolean loginOk = controller.login(userName, password);
        if(loginOk)
            showMainMenu();
        else{
            System.out.println("Incorrect credentials, try again (Y/N)?");
            String input = sc.nextLine();
            if(input.equalsIgnoreCase("y"))
                loginMenu();
            else
                System.exit(1);

        }
    }

    private void showMainMenu() {
        clearScreen();
        printHeader("MAIN MENU");
        List<String> choices = new LinkedList<String>();
        choices.add(choices.size()+1+" - Place new order");
        choices.add(choices.size()+1+" - Register new customer");
        if(controller.getCurrentUserPosition()!=EmployePosition.EMPLOYEE){
            choices.add(choices.size()+1+" - Employees");
            choices.add(choices.size()+1+" - Customers ");
            choices.add(choices.size()+1+" - Stock");
        }
        choices.add("0 - Log out");
        printChoices(choices);
        int choice = getInput(choices.size());
        EmployePosition position = controller.getCurrentUserPosition();

        switch (choice){
            case 6:
                System.out.println("Wrong choice! Try again");
            case -1:
                showMainMenu();
                break;
            case 1:
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
                if(position!=EmployePosition.EMPLOYEE)
                    cutomerMenu();
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

    private void cutomerMenu() {

        printHeader("CUSTOMER");
    }

    private void stockMenu() {

        printHeader("STOCK");
    }

    private void employeeMenu() {

        printHeader("EMPLOYEE");
    }

    private void registerCustomerMenu() {
        printHeader("REGISTER CUSTOMER");

    }

    private void newOrderMenu() {

        printHeader("CREATE ORDER");
        List<Product> products = controller.getAvailableProducts();
        TableCreator.showProductsTable(products);
        int choice = getInput(products.size());
        switch (choice){
            case -1:
                newOrderMenu();
                break;
            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;
            case 6:
                showMainMenu();
                break;
        }

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
                System.out.println("Wrong choice! Try again");
                choice = -1;
            }
        }catch (InputMismatchException e){
            System.out.println("Input mismatch! Try again.");
            choice =-1;
        }
        return  choice;
    }

    private void printWrongChoice(int choice, String choices) {
        System.out.println("Incorrect input: "+choices+"\n for choices: \n"+choices);
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
