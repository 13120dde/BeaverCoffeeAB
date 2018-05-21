package userInterface;

import domainEntities.Location;
import engine.Common;

public class LocalisationStrings {

    private static Location location = Common.getCurrentLocation();

    public static String login() {
        Location location = Common.getCurrentLocation();
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
        Location location = Common.getCurrentLocation();
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
}
