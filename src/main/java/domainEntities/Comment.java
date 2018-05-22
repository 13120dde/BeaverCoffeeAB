package domainEntities;

import java.util.Date;

public class Comment {

    private int employeeId;
    private int employerId;
    private Date date;
    private String comment;

    public void setEmployeeId(int id)
    {
        this.employeeId = id;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
