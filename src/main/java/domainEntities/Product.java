package domainEntities;

import engine.Common;

public class Product {

    private int productId;
    private String productName, unitType, flavour;
    private double price;
    private int volume;

    public Product(int productId, String productName, String unitType, String flavour, double price, int volume) {
        this.productId = productId;
        this.productName = productName;
        this.unitType = unitType;
        this.flavour = flavour;
        this.price = price;
        this.volume = volume;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String toString(){
        String currency = Common.getLocalCurrency();
        return productId+" - "+productName+", "+flavour+", "+volume+" "+unitType+", "+price+currency;
    }
}
