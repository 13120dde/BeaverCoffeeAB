package domainEntities;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId, customerId, employeeId;
    private Date orderDate;
    private Location location;
    private double sum;

    public Order(int orderId, int customerId, int employeeId, Date orderDate, Location location) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.location = location;
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
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

    public void setLocation(Location location) {
        this.location = location;
    }

    public String toString(){
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append("Order id:"+orderId+"\n");
        orderInfo.append("Employee id:"+employeeId+"\n");
        orderInfo.append("Customer id:"+customerId+"\n");
        orderInfo.append("Location:"+location+"\n");
        orderInfo.append("Date:"+orderDate+"\n");
        return orderInfo.toString();
    }
}
