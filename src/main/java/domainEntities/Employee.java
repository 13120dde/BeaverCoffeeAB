package domainEntities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Employee {

    private String name, idNumber;
    private double serviceGrade;
    private Date startDate, endDate;
    private List<String> comments;
    EmployePosition position;
    Location location;

    public Employee(){
        name = "Dummmy";
        idNumber = "840309-****";
        location=Location.SWEDEN;
        serviceGrade = 0.75;
        position = EmployePosition.MANAGER;
        startDate = new Date();

    }

    public Employee(String name, String idNumber, Location location, double serviceGrade, Date startDate, EmployePosition position){
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getServiceGrade() {
        return serviceGrade;
    }

    public void setServiceGrade(double serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public String getStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(startDate);
        return date;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date ="";
        if (endDate!=null)
          date = sdf.format(endDate);
        return date;
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
