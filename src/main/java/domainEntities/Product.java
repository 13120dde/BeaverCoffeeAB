package domainEntities;


public class Product
{

    private String nameSwe, nameEng, unitType;
    private double priceSEK, priceGBP, priceUSD;
    private int volume;

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
    }

    public String getNameSwe()
    {
        return nameSwe;
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

