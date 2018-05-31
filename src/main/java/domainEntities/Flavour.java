package domainEntities;

import engine.Common;

public class Flavour implements Stockable {

    private String nameSwe, nameEng, unitType;
    private int volume, units;

    public Flavour(String nameSwe, String nameEng, String unitType, int volume) {
        this.nameSwe = nameSwe;
        this.nameEng = nameEng;
        this.unitType = unitType;
        this.volume = volume;
    }



    public Flavour(){
        this.nameSwe = "Vanilj";
        this.nameEng = "Vanilla";
        this.unitType = "cl";
        this.volume = 10;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getNameSwe() {
        return nameSwe;
    }

    public void setNameSwe(String nameSwe) {
        this.nameSwe = nameSwe;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        Location location = Common.getCurrentLocation();
        String flavour="";
        switch (location){
            case US:
            case ENGLAND:
                flavour = nameEng;
                break;
            case SWEDEN:
                flavour = nameSwe;
                break;
        }
        return flavour;
    }
}
