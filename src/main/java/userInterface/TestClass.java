package userInterface;

import database.MongoDb;
import domainEntities.Employee;

import java.net.UnknownHostException;

public class TestClass
{
    public static void main(String[] args)
    {
        try
        {
            MongoDb db = new MongoDb();
            Employee e = new Employee();
            e.setName("Pluto");
            db.updateEmployee(e);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

    }
}
