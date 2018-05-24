package userInterface;

import database.MongoDb;
import domainEntities.*;
import engine.Common;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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

//            List l = new ArrayList();
//            Product p = new Product("Kaffe", "Coffee", 10, 2, 3,
//                    "l", 2);
//
//            Product p2 = new Product("Sko", "Shoe", 10, 2, 3,
//                    "kg", 6);
//
//            l.add(p);
//            l.add(p2);
//            Common.setCurrentLocation(Location.SWEDEN);
//            Order o = new Order(12, "12345", 432, new Date(), Location.SWEDEN, l);
//            db.addOrder(o);


//            Date from = new GregorianCalendar(2017, 1,1).getTime();
//            Date to = new GregorianCalendar(2019, 1, 1).getTime();
//
//            List <Customer> l = db.getCustomersByDate(from, to);
//            Customer i = l.get(0);
//            System.out.println(i.getName());
//            System.out.println(i.getOccupation());

            Customer c = db.getCustomerByName("Bongokungen");
            System.out.println(c.getName());
            System.out.println(c.getOccupation());
            System.out.println(c.getIdNumber());

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
