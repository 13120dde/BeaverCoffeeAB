package engine;

import domainEntities.Location;
import domainEntities.Product;

import java.util.LinkedList;

public class BeaverProducts {

    protected static LinkedList<Product> getDomainProducts(){

        LinkedList<Product> products = new LinkedList<Product>();

        double priceSwe = getLocationPriceMultiplier(Location.SWEDEN);
        double priceUK= getLocationPriceMultiplier(Location.ENGLAND);
        double priceUS= getLocationPriceMultiplier(Location.US);

        products.add(new Product("Hela Bönor Espresso Rostad",
                "Whole Bean Espresso Rostad",
                30*priceSwe,
                30*priceUK,
                30*priceUS,
                "cl",40));

        products.add(new Product("Hela Bönor Fransk Rost",
                "Whole Bean French Roast",
                35*priceSwe,
                35*priceUK,
                35*priceUS,
                "cl",40));

        products.add(new Product("Hela Bönor Lätt Rostad",
                "Whole Bean Light Roast",
                29*priceSwe,
                29*priceUK,
                29*priceUS,
                "cl",45));

        products.add(new Product("Bryggkaffe",
        "Brewed Coffee",
        20*priceSwe,
        20*priceUK,
        20*priceUS,
        "cl",33));


        products.add(new Product("Espresso",
        "Espresso",
        25*priceSwe,
        25*priceUK,
        25*priceUS,
        "cl",10));


        products.add(new Product("Kaffe Latte",
        "Latte",
        25*priceSwe,
        25*priceUK,
        25*priceUS,
        "cl",33));


        products.add(new Product("Cappucino",
        "Cappucino",
        25*priceSwe,
        25*priceUK,
        25*priceUS,
        "cl",33));





        return null;
    }

    private static double getLocationPriceMultiplier(Location location) {
        double multiplier=1;
        switch (location){
            case SWEDEN:
                multiplier = 1;
                break;
            case ENGLAND:
                multiplier = 0.09;
            case US:
                multiplier = 0.11;
                break;
        }

        return multiplier;
    }

}
