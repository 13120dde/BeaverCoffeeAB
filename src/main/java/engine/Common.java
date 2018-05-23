package engine;

import domainEntities.Location;
import userInterface.LocalisationStrings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public static Date formatDate2(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFormatted =null;

        try {
            String formattedDate = myFormat.format(inputFormat.parse(date));
            dateFormatted= myFormat.parse(formattedDate);
        } catch (ParseException e) {
            System.out.println(LocalisationStrings.inputMismatch());
            return new Date();
        }
        return dateFormatted;
    }
    public static Date formatDate(String date) {
        Date dateFormatted = new Date();
        int year = Integer.parseInt(date.substring(4));
        int day = Integer.parseInt(date.substring(0,1));
        int month = Integer.parseInt(date.substring(2,3))-1;

        return new GregorianCalendar(year,month,day).getTime();
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
