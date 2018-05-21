package userInterface;

import domainEntities.Location;
import engine.Common;

public class LocalisationStrings {

    private static Location location = Common.getCurrentLocation();

    public static String login() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn= "Login";
                break;
            case SWEDEN:
                toReturn= "Logga in";
                break;
        }
        return toReturn;
    }

    public static String exit() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn= "Exit";
                break;
            case SWEDEN:
                toReturn= "Avsluta";
                break;
        }
        return toReturn;
    }

    public static String headerLogin() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn= "LOGIN";
                break;
            case SWEDEN:
                toReturn= "LOGGA IN";
                break;
        }
        return toReturn;
    }

    public static String employeeName() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn= "Employee name";
                break;
            case SWEDEN:
                toReturn= "Namn på anställd";
                break;
        }
        return toReturn;
    }

    public static String password() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn= "Password";
                break;
            case SWEDEN:
                toReturn= "Lösenord";
                break;
        }
        return toReturn;
    }

    public static String incorrectCredentials() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Incorrect credentials, try again (Y/N)?";
                break;
            case SWEDEN:
                toReturn= "Felaktiga uppgifter, försök igen (Y/N)?";
                break;
        }
        return toReturn;
    }

    public static String headerMainMenu() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="MAIN MENU";
                break;
            case SWEDEN:
                toReturn= "HUVUDMENY";
                break;
        }
        return toReturn;
    }

    public static String placeNewOrder() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn=" - Place new order";
                break;
            case SWEDEN:
                toReturn= "- Skapa en ny order";
                break;
        }
        return toReturn;
    }

    public static String registerNewCustomer() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn=" - Register new cutomer";
                break;
            case SWEDEN:
                toReturn= " - Registrera en ny användare";
                break;
        }
        return toReturn;
    }

    public static String employees() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn=" - Employees";
                break;
            case SWEDEN:
                toReturn= " - Anställda";
                break;
        }
        return toReturn;
    }

    public static String customers() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn=" - Customers";
                break;
            case SWEDEN:
                toReturn= " - Kunder";
                break;
        }
        return toReturn;
    }

    public static String stock() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn=" - Stock";
                break;
            case SWEDEN:
                toReturn= " - Lager";
                break;
        }
        return toReturn;
    }

    public static String logout() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn=" - Log out";
                break;
            case SWEDEN:
                toReturn= " - Logga ut";
                break;
        }
        return toReturn;
    }

    public static String wrongChoice() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Wrong choice! Try again.";
                break;
            case SWEDEN:
                toReturn= "Fel val! Försök igen";
                break;
        }
        return toReturn;
    }

    public static String headerCustomer() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="CUSTOMER";
                break;
            case SWEDEN:
                toReturn= "KUND";
                break;
        }
        return toReturn;
    }

    public static String headerStock() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="STOCK";
                break;
            case SWEDEN:
                toReturn= "LAGER";
                break;
        }
        return toReturn;
    }

    public static String headerEmployee() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="EMPLOYEE";
                break;
            case SWEDEN:
                toReturn= "ANSTÄLLD";
                break;
        }
        return toReturn;
    }

    public static String headerRegisterCustomer() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="REGISTER CUSTOMER";
                break;
            case SWEDEN:
                toReturn= "REGISTRERA KUND";
                break;
        }
        return toReturn;
    }

    public static String customerName() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Name: ";
                break;
            case SWEDEN:
                toReturn= "Namn: ";
                break;
        }
        return toReturn;
    }

    public static String customerId() {
        String toReturn="";
        switch (location){
            case US:
                toReturn="Social security number (SSN)";
                break;
            case ENGLAND:
                toReturn="Id";
                break;
            case SWEDEN:
                toReturn= "Personnummer";
                break;
        }
        return toReturn;
    }

    public static String occupation() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Occupation";
                break;
            case SWEDEN:
                toReturn= "Yrke";
                break;
        }
        return toReturn;
    }

    public static String address() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Address <street,postal code,city>";
                break;
            case SWEDEN:
                toReturn= "Adress <gate,postkod,stad>";
                break;
        }
        return toReturn;
    }

    public static String proceed() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Proceed";
                break;
            case SWEDEN:
                toReturn= "Fortsätt";
                break;
        }
        return toReturn;
    }

    public static String cancel() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Cancel";
                break;
            case SWEDEN:
                toReturn= "Avbryt";
                break;
        }
        return toReturn;
    }

    public static String someThingWrong() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Something went wrong. Try again later";
                break;
            case SWEDEN:
                toReturn= "Något blev fel. Försök igen senare";
                break;
        }
        return toReturn;
    }

    public static String headerCreateOrder() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="CREATE ORDER";
                break;
            case SWEDEN:
                toReturn= "SKAPA ORDER";
                break;
        }
        return toReturn;
    }

    public static String productsInOrder() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="products in order";
                break;
            case SWEDEN:
                toReturn= "produkter i ordern";
                break;
        }
        return toReturn;
    }

    public static String noProductsSelected() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="No products selected";
                break;
            case SWEDEN:
                toReturn= "Inga valda produkter";
                break;
        }
        return toReturn;
    }

    public static String enterUserBarcode() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Enter user's barcode if registered, otherwise leave empty";
                break;
            case SWEDEN:
                toReturn= "Om användaren är registrerad ange streckkoden, annars lämna tom";
                break;
        }
        return toReturn;
    }

    public static String inputMismatch() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Input mismatch! Try again.";
                break;
            case SWEDEN:
                toReturn= "Felaktig inmatning! Försök igen";
                break;
        }
        return toReturn;
    }

    public static String listEmployyesByTime() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Employee listing on time period";
                break;
            case SWEDEN:
                toReturn= "Lista över antsällda över en tidsperiod";
                break;
        }
        return toReturn;
    }

    public static String searchByName() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Search by name";
                break;
            case SWEDEN:
                toReturn= "Sök med namn";
                break;
        }
        return toReturn;
    }

    public static String startDate() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Start date <ddmmyyyy>";
                break;
            case SWEDEN:
                toReturn= "Startdatum <ddmmyyyy>";
                break;
        }
        return toReturn;
    }

    public static String endDate() {
            String toReturn="";
            switch (location){
                case US:
                case ENGLAND:
                    toReturn="End date <ddmmyyyy>";
                    break;
                case SWEDEN:
                    toReturn= "Slutdatum <ddmmyyyy>";
                    break;
            }
            return toReturn;
        }


    public static String cantFindEmployee(String employeeName) {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Cant find employee: "+employeeName;
                break;
            case SWEDEN:
                toReturn= "Kan ej hitta anställd med namnet: "+employeeName;
                break;
        }
        return toReturn;
    }

    public static String empltyEmployeeList() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="Cant fetch employee listing, check input parameters";
                break;
            case SWEDEN:
                toReturn= "Kan ej lista anställda, se över inparametrarna";
                break;
        }
        return toReturn;
    }

    public static String headerEditEmployee() {
        String toReturn="";
        switch (location){
            case US:
            case ENGLAND:
                toReturn="EMPLOYEE INFO";
                break;
            case SWEDEN:
                toReturn= "ANSTÄLLDINFORMATION";
                break;
        }
        return toReturn;
    }
}
