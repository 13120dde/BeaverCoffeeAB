package domainEntities;

import engine.Common;

public class Flavour {

    private String nameSwe, nameEng, unit;
    private int volume;

    public Flavour(String nameSwe, String nameEng, String unit, int volume) {
        this.nameSwe = nameSwe;
        this.nameEng = nameEng;
        this.unit = unit;
        this.volume = volume;
    }

    public Flavour(){
        this.nameSwe = "Vanilj";
        this.nameEng = "Vanilla";
        this.unit = "cl";
        this.volume = 10;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
