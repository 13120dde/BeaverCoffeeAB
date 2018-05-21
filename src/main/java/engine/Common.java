package engine;

import domainEntities.Location;

public class Common {

    private static Location currentLocation;


    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentLocation(Location currentLocation) {
        Common.currentLocation = currentLocation;
    }

    public static String getLocalCurrency(){

        String currency="";
        switch (currentLocation){
            case US:
                currency = "$";
                break;
            case SWEDEN:
                currency ="kr";
                break;
            case ENGLAND:
                currency  ="£";
                break;
        }
        return currency;
    }

    public static String getLocalId() {
        String id ="";
        switch (currentLocation){
            case SWEDEN:
                id ="PERSONNUMMER";
                break;
            case ENGLAND:
                id="     ID     ";
                break;
            case US:
                id="    SSN     ";
                break;
        }
        return id;
    }
}
