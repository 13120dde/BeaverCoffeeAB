package domainEntities;

public class Address
{
    private String street;
    private String zip;
    private String city;
    private Location location;

    public Address(String street, String zip, String city, Location location)
    {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.location = location;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String toString(){
        return street+", "+zip+", "+city;
    }

}
