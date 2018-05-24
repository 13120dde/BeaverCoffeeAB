package engine;

import domainEntities.Flavour;
import domainEntities.Location;
import domainEntities.Product;

import java.util.LinkedList;

public class BeaverProducts {

    protected static LinkedList<Product> getDomainProducts() {

        LinkedList<Product> products = new LinkedList<Product>();

        double priceSwe = getLocationPriceMultiplier(Location.SWEDEN);
        double priceUK = getLocationPriceMultiplier(Location.ENGLAND);
        double priceUS = getLocationPriceMultiplier(Location.US);

        products.add(new Product("Hela Bönor Espresso Rostad",
                "Whole Bean Espresso Rostad",
                130 * priceSwe,
                130 * priceUK,
                130 * priceUS,
                "g", 500));

        products.add(new Product("Hela Bönor Fransk Roast",
                "Whole Bean French Roast",
                135 * priceSwe,
                135 * priceUK,
                135 * priceUS,
                "g", 300));

        products.add(new Product("Hela Bönor Lätt Rostad",
                "Whole Bean Light Roast",
                99 * priceSwe,
                99 * priceUK,
                99 * priceUS,
                "g", 450));


        products.add(new Product("Bryggkaffe",
                "Brewed Coffee",
                20 * priceSwe,
                20 * priceUK,
                20 * priceUS,
                "cl", 33));


        products.add(new Product("Espresso",
                "Espresso",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 10));


        products.add(new Product("Latte - skummjölk",
                "Latte - skim milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));

        products.add(new Product("Latte - sojamjölk",
                "Latte - soy milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));

        products.add(new Product("Latte - mjölk",
                "Latte - milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));

        products.add(new Product("Latte - 2% mjölk",
                "Latte - 2% milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));


        products.add(new Product("Cappucino - skummjölk",
                "Cappucino - skim milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));

        products.add(new Product("Cappucino - sojamjölk",
                "Cappucino - soy milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));

        products.add(new Product("Cappucino - mjölk",
                "Cappucino - milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));

        products.add(new Product("Cappucino - 2% milk",
                "Cappucino - 2% milk",
                25 * priceSwe,
                25 * priceUK,
                25 * priceUS,
                "cl", 33));


        products.add(new Product("Varm Choklad",
                "Hot Chocolate",
                29 * priceSwe,
                29 * priceUK,
                29 * priceUS,
                "cl", 40));

        products.add(new Product("Varm Choklad med Vispgrädde",
                "Hot Chocolate - Whipped Cream",
                29 * priceSwe,
                29 * priceUK,
                29 * priceUS,
                "cl", 40));


        products.get(0).setEligibleForDiscount(false);
        products.get(1).setEligibleForDiscount(false);
        products.get(2).setEligibleForDiscount(false);
        products.get(0).setFlavorEnabled(false);
        products.get(1).setFlavorEnabled(false);
        products.get(2).setFlavorEnabled(false);


        return products;
    }

    protected static LinkedList<Flavour> getDomainFlavours(){
        LinkedList<Flavour> flavours = new LinkedList<Flavour>();
        flavours.add(new Flavour("Vanilj","Vanilla","cl",10));
        flavours.add(new Flavour("Karamell","Caramel","cl",10));
        flavours.add(new Flavour("Irländsk Kräm","Irish Cream","cl",10));
        return flavours;
    }

    private static double getLocationPriceMultiplier(Location location) {
        double multiplier = 1;
        switch (location) {
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
