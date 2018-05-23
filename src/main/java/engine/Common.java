package engine;

import domainEntities.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
                currency = "USD";
                break;
            case SWEDEN:
                currency ="SEK";
                break;
            case ENGLAND:
                currency  ="GBP";
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

    public static Date formatDate(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFormatted =null;

        try {
            String formattedDate = myFormat.format(inputFormat.parse(date));
            dateFormatted= myFormat.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }

    public static String getLocalCurrency(Location location) {
        String currency="";
        switch (location){
            case US:
                currency = "USD";
                break;
            case SWEDEN:
                currency ="SEK";
                break;
            case ENGLAND:
                currency  ="GBP";
                break;
        }
        return currency;
    }
}
