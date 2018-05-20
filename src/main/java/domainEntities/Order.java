package domainEntities;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId, customerId, employeeId;
    private Date orderDate;
    private String location;
    private List<Product> products;

    public Order(int orderId, int customerId, int employeeId, Date orderDate, String location, List<Product> products) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.location = location;
        this.products = products;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String toString(){
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append("Order id:"+orderId+"\n");
        orderInfo.append("Employee id:"+employeeId+"\n");
        orderInfo.append("Customer id:"+customerId+"\n");
        orderInfo.append("Location:"+location+"\n");
        orderInfo.append("Date:"+orderDate+"\n");
        orderInfo.append("### PRODUCTS ###");
        for(Product p : products){
            orderInfo.append(products.toString()+"\n");
        }
        orderInfo.append("################");
        return orderInfo.toString();
    }
}
