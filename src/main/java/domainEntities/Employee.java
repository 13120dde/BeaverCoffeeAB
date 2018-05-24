package domainEntities;

import engine.Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Employee {

    private String name, idNumber, password;
    private int serviceGrade, id;
    private Date startDate, endDate;
    private List<Comment> comments;
    EmployePosition position;
    Location location;

    public Employee(){
        name = "Dummmy";
        idNumber = "840309-****";
        location=Location.SWEDEN;
        serviceGrade = 75;
        position = EmployePosition.CORPORATE_SALES;
        startDate = new Date();
        comments = new LinkedList<Comment>();
        id = new Random().nextInt(1000);

        Comment c = new Comment();
        c.setEmployerId(0);
        c.setDate(new Date());
        c.setComment("Lazy bum, always late!");
        addComment(c);
        c = new Comment();
        c.setEmployerId(1);
        c.setDate(new Date());
        c.setComment(" some comment here");
        addComment(c);

    }

    public Employee(String name, String idNumber, Location location, int serviceGrade, Date startDate, Date endDate, EmployePosition position){
        this.name = name;
        this.idNumber=idNumber;
        this.location=location;
        this.serviceGrade=serviceGrade;
        this.startDate=startDate;
        this.position=position;
        this.endDate=endDate;
        password ="admin";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getServiceGrade() {
        return serviceGrade;
    }

    public void setServiceGrade(int serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = Common.formatDate(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate= Common.formatDate(endDate);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public EmployePosition getPosition() {
        return position;
    }

    public void setPosition(EmployePosition position) {
        this.position = position;
    }
}
