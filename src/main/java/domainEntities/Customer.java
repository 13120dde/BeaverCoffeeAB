package domainEntities;

import domainEntities.Order;
import engine.Common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Customer {

    private String name, occupation, barcode, idNumber, address;
    private int totalPurchases;
    private Date registeredDate;
    private List<Order> orders;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisteredDate() {
        SimpleDateFormat sdf = Common.getSimpleDateFormat();
        String date = sdf.format(registeredDate);
        return date;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = Common.formatDate(registeredDate);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Customer(String name, String occupation, String barcode, String idNumber, String address, String registeredDate, List<Order> orders) {
        this.name = name;
        this.occupation = occupation;
        this.barcode = barcode;
        this.idNumber = idNumber;
        this.address = address;
        this.registeredDate =Common.formatDate(registeredDate);
        this.orders = orders;
    }

    public Customer(){
        this.name = "Kalle";
        this.occupation = "Coffe critic";
        this.barcode = "12345";
        this.idNumber = "111";
        this.address = "Coffe boulevard";
        this.registeredDate = Common.formatDate("12042018");
        this.orders = new LinkedList<Order>();
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

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int amount){
        totalPurchases=amount;
    }
}
