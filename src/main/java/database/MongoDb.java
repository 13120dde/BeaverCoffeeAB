package database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import domainEntities.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDb
{

    private MongoClient mongoClient;
    private MongoDatabase mongoDb;
    private CodecRegistry pojoCodecRegistry;

    public MongoDb() throws UnknownHostException
    {
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        mongoDb = mongoClient.getDatabase("BeaverCoffee");


    }

    public Employee login(String userName)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        Document employee = collection.find(eq("name", userName)).first();
        Employee employeeToReturn = null;
        if (employee != null)
        {
            String loc = employee.getString("location");
            Location location = Enum.valueOf(Location.class, loc);
            String pos = employee.getString("position");
            EmployePosition position = Enum.valueOf(EmployePosition.class, pos);
            employeeToReturn = new Employee(userName,
                    employee.getString("ssn"),
                    location,
                    employee.getInteger("service_grade"),
                    employee.getDate("start_date"),
                    employee.getDate("start_date"),
                    position
            );
            String password = employee.getString("password");
            employeeToReturn.setPassword(password);
        }
        return employeeToReturn;
    }

    public boolean addProduct(Product product)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("product");
        Document doc = new Document("name_swe", product.getNameSwe())
                .append("name_eng", product.getNameEng())
                .append("price_sek", product.getPriceSEK())
                .append("price_gbp", product.getPriceGBP())
                .append("price_usd", product.getPriceUSD())
                .append("unit", product.getUnitType())
                .append("volume", product.getVolume())
                .append("eligible_discount", product.isEligibleForDiscount())
                .append("flavour_enabled", product.isFlavorEnabled());
        collection.insertOne(doc);

        return true;
    }

    public LinkedList<Product> getAvailableProducts()
    {
        MongoCollection<Document> collection = mongoDb.getCollection("product");
        List<Document> documents = collection.find().into(new LinkedList<Document>());
        List<Product> availableProducts = new LinkedList<Product>();
        for (Document document : documents)
        {
            Product product = new Product(document.getString("name_swe"),
                    document.getString("name_eng"),
                    document.getDouble("price_sek"),
                    document.getDouble("price_gbp"),
                    document.getDouble("price_usd"),
                    document.getString("unit"),
                    document.getInteger("volume"));
            product.setEligibleForDiscount(document.getBoolean("eligible_discount"));
            product.setFlavorEnabled(document.getBoolean("flavour_enabled"));
            availableProducts.add(product);
        }
        System.out.println();
        return (LinkedList<Product>) availableProducts;
    }

    public boolean addEmployee(Employee employee)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        Document doc = new Document("name", employee.getName())
                .append("ssn", employee.getIdNumber())
                .append("position", employee.getPosition().name())
                .append("start_date", employee.getStartDate())
                .append("end_date", employee.getEndDate())
                .append("location", employee.getLocation().name())
                .append("service_grade", employee.getServiceGrade())
                .append("password", employee.getPassword())
                .append("comments", "");
        collection.insertOne(doc);


        return true;
    }

    //TODO Behöver skilja på brewed och bagged coffee för gratis kaffe samt räkna antal
    //TODO Order in i customer?
    public boolean addOrder(Order order)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("order");
        Document doc = new Document("barcode", order.getCustomerBarcode())
                .append("employee_id", order.getEmployeeId())
                .append("date", order.getOrderDate())
                .append("location", order.getLocation().name())
                .append("products", order.getProducts());

        collection.insertOne(doc);
        increasePurchases(order.getCustomerBarcode());
        return true;
    }

    public boolean addCustomer(Customer customer)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        Document doc = new Document("name", customer.getName())
                .append("ssn", customer.getIdNumber())
                .append("barcode", customer.getBarcode())
                .append("occupation", customer.getOccupation())
                .append("date", customer.getRegisteredDate())
                .append("counter", 0)
                .append("date", customer.getRegisteredDate())
                .append("address", customer.getAddress());
        collection.insertOne(doc);


        return true;
    }

    public boolean updateCustomer(Customer customer)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        Bson filter = new Document("ssn", customer.getIdNumber());
        Bson newValue = new Document("name", customer.getName())
                .append("ssn", customer.getIdNumber())
                .append("barcode", customer.getBarcode())
                .append("occupation", customer.getOccupation())
                .append("date", customer.getRegisteredDate())
                .append("counter", customer.getTotalPurchases())
                .append("date", customer.getRegisteredDate())
                .append("address", customer.getAddress());

        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);

        return true;
    }

    //TODO int som parameter för antar drycker köpta
    public boolean increasePurchases(String barcode)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        Bson filter = new Document("barcode", barcode);
        Bson newValue = new Document("counter", 1);
        Bson updateOperationDocument = new Document("$inc", newValue);
        collection.updateOne(filter, updateOperationDocument);

        return true;
    }

    public boolean updateEmployee(Employee employee)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        Bson filter = new Document("ssn", employee.getIdNumber());
        Bson newValue = new Document("name", employee.getName())
                .append("ssn", employee.getIdNumber())
                .append("position", employee.getPosition().name())
                .append("start_date", employee.getStartDate())
                .append("end_date", employee.getEndDate())
                .append("location", employee.getLocation().name())
                .append("service_grade", employee.getServiceGrade());
        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);

        return true;
    }

    public List getCustomersByDate(Date from, Date to)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to))).iterator();

        List<Customer> customerList = new ArrayList<Customer>();
        try
        {
            while (cursor.hasNext())
            {

                Document d = cursor.next();
                String name = d.getString("name");
                String occupation = d.getString("occupation");
                String barcode = d.getString("barcode");
                String idNumber = d.getString("ssn");
                Date registered = d.getDate("date");

                Object address = d.get("address");
                Document addressDoc = (Document) address;
                String city = addressDoc.getString("street");
                String location = addressDoc.getString("location");
                String street = addressDoc.getString("street");
                String zip = addressDoc.getString("zip");

                Location l = Enum.valueOf(Location.class, location);
                Address ad = new Address(street, zip, city, l);
                Customer c = new Customer(name, occupation, barcode, idNumber, ad, registered);
                customerList.add(c);
            }
        } finally
        {
            cursor.close();
        }
        return customerList;
    }

    public List getCustomersByDateandLocation(Date from, Date to, Location location)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to)).append("address.location", location.name())).iterator();

        List<Customer> customerList = new ArrayList<Customer>();
        try
        {
            while (cursor.hasNext())
            {

                Document d = cursor.next();
                String name = d.getString("name");
                String occupation = d.getString("occupation");
                String barcode = d.getString("barcode");
                String idNumber = d.getString("ssn");
                Date registered = d.getDate("date");

                Object address = d.get("address");
                Document addressDoc = (Document) address;
                String city = addressDoc.getString("street");
                String loc = addressDoc.getString("location");
                String street = addressDoc.getString("street");
                String zip = addressDoc.getString("zip");

                Location l = Enum.valueOf(Location.class, loc);
                Address ad = new Address(street, zip, city, l);
                Customer c = new Customer(name, occupation, barcode, idNumber, ad, registered);
                customerList.add(c);
            }
        } finally
        {
            cursor.close();
        }
        return customerList;
    }

    public List getEmployeesByDate(Date from, Date to)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to))).iterator();

        List<Employee> employeeList = new ArrayList<Employee>();
        try
        {
            while (cursor.hasNext())
            {

                Document d = cursor.next();
                String name = d.getString("name");
                String idNumber = d.getString("ssn");
                String position = d.getString("position");
                int serviceGrade = d.getInteger("service_grade");
                Date startDate = d.getDate("start_date");
                Date endDate = d.getDate("end_date");
                String location = d.getString("location");

                Employee e = new Employee(name, idNumber, Enum.valueOf(Location.class, location), serviceGrade, startDate, endDate,
                        Enum.valueOf(EmployePosition.class, position));

                employeeList.add(e);
            }
        } finally
        {
            cursor.close();
        }
        return employeeList;
    }

    public List getEmployeesByDateAndLocation(Date from, Date to, Location location)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        MongoCursor<Document> cursor = collection.find(new Document("start_date", new Document("$gte", from)
                .append("$lt", to)).append("location", location.name())).iterator();

        List<Employee> employeeList = new ArrayList<Employee>();
        try
        {
            while (cursor.hasNext())
            {

                Document d = cursor.next();
                String name = d.getString("name");
                String idNumber = d.getString("ssn");
                String position = d.getString("position");
                int serviceGrade = d.getInteger("service_grade");
                Date startDate = d.getDate("start_date");
                Date endDate = d.getDate("end_date");
                String loc = d.getString("location");

                Employee e = new Employee(name, idNumber, Enum.valueOf(Location.class, loc), serviceGrade, startDate, endDate,
                        Enum.valueOf(EmployePosition.class, position));

                employeeList.add(e);
            }
        } finally
        {
            cursor.close();
        }
        return employeeList;
    }

    //SSN hårdkodat
    public boolean addComment(Comment comment)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        collection.updateOne(eq("ssn", "840309-****"), new Document("$push", new Document("comment", comment)));
        return true;
    }


    public boolean addFlavour(Flavour f)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("flavour");
        Document doc = new Document("name_swe", f.getNameSwe())
                .append("name_eng", f.getNameEng())
                .append("unit", f.getUnit())
                .append("volume", f.getVolume());
        collection.insertOne(doc);

        return true;
    }

    public Customer getCustomerByName(String customerName)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        Document myDoc = collection.find(eq("name", customerName)).first();

        String name = myDoc.getString("name");
        String occupation = myDoc.getString("occupation");
        String barcode = myDoc.getString("barcode");
        String idNumber = myDoc.getString("ssn");
        Date registered = myDoc.getDate("date");

        Object address = myDoc.get("address");
        Document addressDoc = (Document) address;
        String city = addressDoc.getString("street");
        String location = addressDoc.getString("location");
        String street = addressDoc.getString("street");
        String zip = addressDoc.getString("zip");

        Location l = Enum.valueOf(Location.class, location);
        Address ad = new Address(street, zip, city, l);
        Customer c = new Customer(name, occupation, barcode, idNumber, ad, registered);

        return c;
    }

    public Employee getEmployeeByName(String employeeName)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        Document myDoc = collection.find(eq("name", employeeName)).first();


        String name = myDoc.getString("name");
        String idNumber = myDoc.getString("ssn");
        String position = myDoc.getString("position");
        int serviceGrade = myDoc.getInteger("service_grade");
        Date startDate = myDoc.getDate("start_date");
        Date endDate = myDoc.getDate("end_date");
        String location = myDoc.getString("location");

        Employee e = new Employee(name, idNumber, Enum.valueOf(Location.class, location), serviceGrade, startDate, endDate,
                Enum.valueOf(EmployePosition.class, position));

        return e;
    }

    public List<Flavour> getAvailableFlavours()
    {
        MongoCollection<Document> collection = mongoDb.getCollection("flavour");
        List<Document> documents = collection.find().into(new LinkedList<Document>());
        List<Flavour> availableFlavours = new LinkedList<Flavour>();
        for (Document document : documents)
        {
            Flavour flavour = new Flavour(document.getString("name_swe"),
                    document.getString("name_eng"),
                    document.getString("unit"),
                    document.getInteger("volume"));
            availableFlavours.add(flavour);
        }
        System.out.println();
        return (LinkedList<Flavour>) availableFlavours;
    }
}
