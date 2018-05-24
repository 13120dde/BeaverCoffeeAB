package domainEntities;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Order {
    private ObjectId orderId, employeeId;
    private Date orderDate;
    private Location location;
    private double sum;
    private String customerBarcode;
    private boolean employeeDiscount;
    private List <Product> products;

    public Order(String customerBarcode, ObjectId employeeId, Date orderDate,
                    Location location, List <Product> products) {

        this.customerBarcode= customerBarcode;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.location = location;
        this.products = products;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getCustomerBarcode() {
        return customerBarcode;
    }

    public void setCustomerBarcode(String customerBarcode) {
        this.customerBarcode = customerBarcode;
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public void setOrderId(ObjectId orderId) {
        this.orderId = orderId;
    }


    public ObjectId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(ObjectId employeeId) {
        this.employeeId = employeeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Location getLocation() {
        return location;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String toString(){
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append("Order id:"+orderId+"\n");
        orderInfo.append("Employee id:"+employeeId+"\n");
        orderInfo.append("Customer barcode:"+customerBarcode+"\n");
        orderInfo.append("Location:"+location+"\n");
        orderInfo.append("Date:"+orderDate+"\n");
        return orderInfo.toString();
    }

    public boolean getEmployeeDiscount() {
        return employeeDiscount;
    }

}
