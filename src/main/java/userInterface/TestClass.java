package userInterface;

import database.MongoDb;
import domainEntities.Comment;
import domainEntities.Customer;
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
//            e.setName("Sven");
//            db.updateEmployee(e);
//            Comment c = new Comment();
//            c.setComment("Alltid sen");
//            c.setDate(new Date());
//            c.setEmployerId(22);
//            db.addComment(c);
            Customer c = new Customer();
            db.addCustomer(c);

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
