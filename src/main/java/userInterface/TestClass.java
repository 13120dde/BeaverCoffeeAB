package userInterface;

import database.MongoDb;
import domainEntities.*;
import engine.Common;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
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
//            Common.setCurrentLocation(Location.SWEDEN);
//            Product p2 = new Product("Potatis",
//                    "Potato",
//                    130 ,
//                    130 ,
//                    130 ,
//                    "g", 500);

//            db.addProduct(p2);
//            p2.setUnits(10);
//            db.addProductToStock(Location.SWEDEN,p2);
//
//            l.add(p);
//            l.add(p2);
//            Common.setCurrentLocation(Location.SWEDEN);
//            Order o = new Order(12, "12345", 432, new Date(), Location.SWEDEN, l);
//            db.addOrder(o);




            Date from = new GregorianCalendar(2017, 1,1).getTime();
            Date to = new GregorianCalendar(2019, 1, 1).getTime();

//            Employee employeeTest = new Employee("Test",
//                    "19840309****",
//                    Location.ENGLAND,
//                    100,
//                    Common.getCurrentDate(),
//                    to,
//                    EmployePosition.EMPLOYEE);
//
//            db.addEmployee(employeeTest);

            ObjectId id1 = new ObjectId("5b0d3398795b9d3274382f1f");
            ObjectId id2 = new ObjectId("5b0d3398795b9d3274382f1f");
//
//            Comment c = new Comment(id1, id2, from, "HA§§§§§§§§HA");
//            db.addComment(c);

//            List <Comment> comments = db.getComments(id1);
//
//            for (Comment c: comments)
//            {
//                System.out.println(c.getComment());
//            }

//            Employee e = new Employee();
//            e.setEndDate(to);
//            e.setPosition(EmployePosition.MANAGER);
//            db.addEmployee(e);
//

//            List <Employee> list = db.getEmployeesByDate(from, to);
//            System.out.println(list.get(0).getName());

//            List <Employee> l = db.getEmployeesByDateAndLocation(from, to, Location.SWEDEN);
//            Employee i = l.get(0);
//            System.out.println(i.getName());
//            System.out.println(i.getPosition().name());
//            ObjectId id = new ObjectId("5b06d5b142e7361bb4e0af3e");
//            List <Order> list = db.getOrdersMadeByEmployee(id, from, to);
//            Order ett = list.get(0);
//            System.out.println(ett.toString());
//            List <Product> e1 = ett.getProducts();
//            Product p = e1.get(0);
//            Product p1 = e1.get(1);
//            System.out.println(p.getNameSwe());
//            System.out.println(p1.getNameSwe());
//            System.out.println(p.getPriceUSD());
//            System.out.println(p1.getPriceUSD());

//            List <Product> list = db.getSalesOverTimePeriod(from, to, Location.SWEDEN);
//
//            for (Product p: list)
//                System.out.println(p.getNameSwe());

//            Customer c = db.getCustomerByName("Bongokungen");
//            System.out.println(c.getName());
//            System.out.println(c.getOccupation());
//            System.out.println(c.getIdNumber());

//            List <Product> list = db.getSalesOverTimePeriod(from, to);
//
//            for (Product p : list)
//                System.out.println(p.getNameEng());

            List<StockItem> list = db.getStock(Location.SWEDEN);

            for (StockItem s : list)
            {
                System.out.println(s.getNameEng());
            }

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
