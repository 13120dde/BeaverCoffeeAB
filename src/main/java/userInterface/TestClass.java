package userInterface;

import database.MongoDb;
import domainEntities.*;
import engine.Common;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//            Customer c = new Customer();
//            c.setName("Bongokungen");
//            c.setOccupation("Bagare");
//            db.increasePurchases("12345");

            List l = new ArrayList();
            Product p = new Product("Kaffe", "Coffee", 10, 2, 3,
                    "l", 2);

            Product p2 = new Product("Sko", "Shoe", 10, 2, 3,
                    "kg", 6);

            l.add(p);
            l.add(p2);
            Common.setCurrentLocation(Location.SWEDEN);
            Order o = new Order(12, 123, 432, new Date(), "SWEDEN", l);
            db.addOrder(o);



        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
