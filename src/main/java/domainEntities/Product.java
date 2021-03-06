package domainEntities;


import engine.Common;

public class Product implements Stockable
{

    private String nameSwe, nameEng, unitType;
    private double priceSEK, priceGBP, priceUSD;
    private int volume;
    private Flavour flavour;
    private boolean eligibleForDiscount, flavorEnabled;
    private int units;

    public Product(String nameSwe, String nameEng, double priceSEK, double priceGBP, double priceUSD,
                   String unitType, int volume)
    {
        this.nameSwe = nameSwe;
        this.nameEng = nameEng;
        this.priceSEK = priceSEK;
        this.priceGBP = priceGBP;
        this.priceUSD = priceUSD;
        this.unitType = unitType;
        this.volume = volume;
        flavour = null;
        eligibleForDiscount = true;
        flavorEnabled = true;

    }

    public int getUnits()
    {
        return units;
    }

    public void setUnits(int units)
    {
        this.units = units;
    }

    public boolean isFlavorEnabled() {
        return flavorEnabled;
    }

    public void setFlavorEnabled(boolean flavorEnabled) {
        this.flavorEnabled = flavorEnabled;
    }

    public boolean isEligibleForDiscount() {
        return eligibleForDiscount;
    }

    public void setEligibleForDiscount(boolean eligibleForDiscount) {
        this.eligibleForDiscount = eligibleForDiscount;
    }

    public Flavour getFlavour() {
        return flavour;
    }

    public void setFlavour(Flavour flavour) {
        this.flavour = flavour;
    }

    public String getNameSwe()
    {
        return nameSwe;
    }

    public String getProductName(){
        Location location = Common.getCurrentLocation();
        String productName="";
        switch (location){
            case US:
            case ENGLAND:
                productName = nameEng;
                break;
            case SWEDEN:
                productName = nameSwe;
                break;
        }
        return productName;
    }

    public double getPrice(){
        Location location = Common.getCurrentLocation();
        double price=0;
        switch (location){
            case US:
                price =  priceUSD;
                break;
            case ENGLAND:
                price =  priceGBP;
                break;
            case SWEDEN:
                price =  priceSEK;
                break;
        }
        return price;
    }

    public void setNameSwe(String nameSwe)
    {
        this.nameSwe = nameSwe;
    }

    public String getNameEng()
    {
        return nameEng;
    }

    public void setNameEng(String nameEng)
    {
        this.nameEng = nameEng;
    }

    public String getUnitType()
    {
        return unitType;
    }

    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public double getPriceSEK()
    {
        return priceSEK;
    }

    public void setPriceSEK(double priceSEK)
    {
        this.priceSEK = priceSEK;
    }

    public double getPriceGBP()
    {
        return priceGBP;
    }

    public void setPriceGBP(double priceGBP)
    {
        this.priceGBP = priceGBP;
    }

    public double getPriceUSD()
    {
        return priceUSD;
    }

    public void setPriceUSD(double priceUSD)
    {
        this.priceUSD = priceUSD;
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }


//    public String toString(){
//        String currency = Common.getLocalCurrency();
//        return productId+" - "+productName+", "+flavour+", "+volume+" "+unitType+", "+price+currency;
//    }

}

