package stakeholders;

import domainEntities.Order;

import java.util.Date;
import java.util.List;

public class Customer {

    private String name, occupation, barcode, idNumber, address;
    private Date registeredDate;
    private List<Order> orders;

    public Customer(String name, String occupation, String barcode, String idNumber, String address, Date registeredDate, List<Order> orders) {
        this.name = name;
        this.occupation = occupation;
        this.barcode = barcode;
        this.idNumber = idNumber;
        this.address = address;
        this.registeredDate = registeredDate;
        this.orders = orders;
    }

    public boolean eligibleForDiscount(){
        if ( orders.size() % 10 == 0)
            return true;
        return false;
    }

    public String toString(){
        return "Id number: "+idNumber+"\n+" +
                "Name: "+name+"\n"+
                "Occupation: "+occupation+"\n"+
                "Barcode: "+barcode+"\n"+
                "Address: "+address+"\n"+
                "Date of registration: "+registeredDate+"\n"+
                "Number of orders: "+orders.size();
    }

}
