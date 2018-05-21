package domainEntities;

import java.util.Date;
import java.util.List;

public class Employee {

    private String name, idNumber, location;
    private double serviceGrade;
    private Date startDate, endDate;
    private List<String> comments;
    EmployePosition position;

    public Employee(){
        name = "Dummmy";
        idNumber = "840309-****";
        location="Sweden";
        serviceGrade = 0.75;
        position = EmployePosition.BRANCH_LOCATION_MANAGER;

    }

    public Employee(String name, String idNumber, String location, double serviceGrade, Date startDate, EmployePosition position){
        this.name = name;
        this.idNumber=idNumber;
        this.location=location;
        this.serviceGrade=serviceGrade;
        this.startDate=startDate;
        this.position=position;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getServiceGrade() {
        return serviceGrade;
    }

    public void setServiceGrade(double serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public EmployePosition getPosition() {
        return position;
    }

    public void setPosition(EmployePosition position) {
        this.position = position;
    }
}
