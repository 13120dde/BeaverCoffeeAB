package domainEntities;

import org.bson.types.ObjectId;

import java.util.Date;

public class Comment {

    private ObjectId employeeId;
    private ObjectId authorId;
    private Date date;
    private String comment;

    public Comment(ObjectId employeeId, ObjectId authorId, Date date, String comment )
    {
        this.employeeId = employeeId;
        this.authorId = authorId;
        this.date = date;
        this.comment = comment;
    }

    public void setEmployeeId(ObjectId id)
    {
        this.employeeId = id;
    }

    public ObjectId getEmployeeId()
    {
        return employeeId;
    }

    public ObjectId getAuthorId() {
        return authorId;
    }

    public void setAuthorId(ObjectId authorId) {
        this.authorId = authorId;
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
