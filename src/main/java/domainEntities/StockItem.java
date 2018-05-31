package domainEntities;

public class StockItem
{
    private String nameEng;
    private String nameSwe;
    private int volume;
    private String unitType;
    private int units;

    public StockItem(String nameEng, String nameSwe, int volume, String unitType, int units)
    {
        this.nameEng = nameEng;
        this.nameSwe = nameSwe;
        this.volume = volume;
        this.unitType = unitType;
        this.units = units;
    }

    public String getNameEng()
    {
        return nameEng;
    }

    public void setNameEng(String nameEng)
    {
        this.nameEng = nameEng;
    }

    public String getNameSwe()
    {
        return nameSwe;
    }

    public void setNameSwe(String nameSwe)
    {
        this.nameSwe = nameSwe;
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }

    public String getUnitType()
    {
        return unitType;
    }

    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public int getUnits()
    {
        return units;
    }

    public void setUnits(int units)
    {
        this.units = units;
    }
}
