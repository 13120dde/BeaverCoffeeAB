package userInterface;

import database.MongoDb;
import domainEntities.Comment;
import domainEntities.Employee;

import java.net.UnknownHostException;
import java.util.Date;

public class TestClass
{
    public static void main(String[] args)
    {
        try
        {
            MongoDb db = new MongoDb();
//            Employee e = new Employee();
//            e.setName("Pluto");
            Comment c = new Comment();
            c.setComment("Alltid sen");
            c.setDate(new Date());
            c.setEmployerId(22);
            db.addComment(c);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

    }
}
