package database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import domainEntities.*;
import engine.BeaverProducts;
import engine.Controller;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;
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
                    employee.getDate("end_date"),
                    position
            );
            String password = employee.getString("password");
            ObjectId id = employee.getObjectId("_id");
            employeeToReturn.setObjectId(id);
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
                .append("password", employee.getPassword());
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
                .append("products", order.getProducts())
                .append("sum", order.getSum())
                .append("employee_discount", order.getEmployeeDiscount());

        collection.insertOne(doc);
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
    public boolean increasePurchases(String barcode, int counter)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        Bson filter = new Document("barcode", barcode);
        Bson newValue = new Document("counter", counter);
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

    public Customer getCustomerByBarcode(String barcode)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("customer");
        Document myDoc = collection.find(eq("barcode", barcode)).first();

        String name = myDoc.getString("name");
        String occupation = myDoc.getString("occupation");
        String barcode2 = myDoc.getString("barcode");
        String idNumber = myDoc.getString("ssn");
        Date registered = myDoc.getDate("date");
        int counter = myDoc.getInteger("counter");

        Object address = myDoc.get("address");
        Document addressDoc = (Document) address;
        String city = addressDoc.getString("street");
        String location = addressDoc.getString("location");
        String street = addressDoc.getString("street");
        String zip = addressDoc.getString("zip");


        Location l = Enum.valueOf(Location.class, location);
        Address ad = new Address(street, zip, city, l);
        Customer c = new Customer(name, occupation, barcode2, idNumber, ad, registered);
        c.setTotalPurchases(counter);

        return c;
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
                String city = addressDoc.getString("city");
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
        MongoCursor<Document> cursor = collection.find(new Document("start_date", new Document("$gte", from)
                .append("$lt", to))).iterator();

        List<Employee> employeeList = new ArrayList<Employee>();
        try
        {
            while (cursor.hasNext())
            {
                System.out.println("Found one!");

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

    public boolean addComment(Comment comment)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        collection.updateOne(eq("_id", comment.getEmployeeId()), new Document("$push", new Document("comments", comment)));
        return true;
    }

    public List<Comment> getComments(ObjectId employeeId)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        Document employeeDoc = collection.find(eq("_id", employeeId)).first();
        List<Document> comments = (List<Document>) employeeDoc.get("comments");

        List<Comment> commentList = new ArrayList<Comment>();

        for (Document c : comments)
        {
            ObjectId authorId = c.getObjectId("authorId");
            String comment = c.getString("comment");
            Date date = c.getDate("date");
            Comment com = new Comment(employeeId, authorId, date, comment);
            commentList.add(com);
        }

        return commentList;
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
        if (myDoc == null)
            return null;

        String name = myDoc.getString("name");
        String occupation = myDoc.getString("occupation");
        String barcode = myDoc.getString("barcode");
        String idNumber = myDoc.getString("ssn");
        Date registered = myDoc.getDate("date");
        int counter = myDoc.getInteger("counter");

        Object address = myDoc.get("address");
        Document addressDoc = (Document) address;
        String city = addressDoc.getString("street");
        String location = addressDoc.getString("location");
        String street = addressDoc.getString("street");
        String zip = addressDoc.getString("zip");

        Location l = Enum.valueOf(Location.class, location);
        Address ad = new Address(street, zip, city, l);
        Customer c = new Customer(name, occupation, barcode, idNumber, ad, registered);
        c.setTotalPurchases(counter);

        return c;
    }

    public Employee getEmployeeByName(String employeeName)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("employee");
        Document myDoc = collection.find(eq("name", employeeName)).first();

        if (myDoc == null)
            return null;

        String name = myDoc.getString("name");
        String idNumber = myDoc.getString("ssn");
        String position = myDoc.getString("position");
        int serviceGrade = myDoc.getInteger("service_grade");
        Date startDate = myDoc.getDate("start_date");
        Date endDate = myDoc.getDate("end_date");
        String location = myDoc.getString("location");

        Employee e = new Employee(name, idNumber, Enum.valueOf(Location.class, location), serviceGrade, startDate, endDate,
                Enum.valueOf(EmployePosition.class, position));
        e.setObjectId(myDoc.getObjectId("_id"));
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

    public List getOrdersMadeByEmployee(ObjectId employeeId, Date from, Date to)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("order");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to)).append("employee_id", employeeId)).iterator();

        List<Order> orderList = new ArrayList<Order>();
        try
        {
            while (cursor.hasNext())
            {
                Document d = cursor.next();
                ObjectId id = d.getObjectId("_id");
                ObjectId employeeId2 = d.getObjectId("employee_id");
                String barcode = d.getString("barcode");
                Date date = d.getDate("date");
                Location location = Enum.valueOf(Location.class, d.getString("location"));
                double sum = d.getDouble("sum");
                boolean emploeyeeDiscount = d.getBoolean("employee_discount");

                List<Document> prods = (List<Document>) d.get("products");
                Product p;
                List<Product> productList = new ArrayList<Product>();

                for (Document prod : prods)
                {
                    String nameSwe = prod.getString("nameSwe");
                    String nameEng = prod.getString("nameEng");
                    double priceSEK = prod.getDouble("priceSEK");
                    double priceGBP = prod.getDouble("priceGBP");
                    double priceUSD = prod.getDouble("priceUSD");
                    String unit = prod.getString("unitType");
                    int volume = prod.getInteger("volume");

                    p = new Product(nameSwe, nameEng, priceSEK, priceGBP, priceUSD, unit, volume);
                    productList.add(p);
                }

                Order o = new Order(barcode, employeeId2, date, location, productList);
                o.setSum(sum);
                o.setEmployeeDiscount(emploeyeeDiscount);
                //String customerBarcode, ObjectId employeeId, Date orderDate,
                //                    Location location, List <Product> product
//                o.setOrderId(id);

                orderList.add(o);
            }
        } finally
        {
            cursor.close();
        }
        return orderList;
    }

    public List getSalesOverTimePeriodwithLocation(Date from, Date to, Location location)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("order");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to)).append("location", location.name())).iterator();

        List<Product> productList = new ArrayList<Product>();

        while (cursor.hasNext())
        {
            Document d = cursor.next();

            List<Document> prods = (List<Document>) d.get("products");
            Product p;

            for (Document prod : prods)
            {
                String nameSwe = prod.getString("nameSwe");
                String nameEng = prod.getString("nameEng");
                double priceSEK = prod.getDouble("priceSEK");
                double priceGBP = prod.getDouble("priceGBP");
                double priceUSD = prod.getDouble("priceUSD");
                String unit = prod.getString("unitType");
                int volume = prod.getInteger("volume");

                p = new Product(nameSwe, nameEng, priceSEK, priceGBP, priceUSD, unit, volume);
                productList.add(p);
            }
        }
        return productList;
    }

    public List getSalesOverTimePeriod(Date from, Date to)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("order");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to))).iterator();

        List<Product> productList = new ArrayList<Product>();

        while (cursor.hasNext())
        {
            Document d = cursor.next();

            List<Document> prods = (List<Document>) d.get("products");
            Product p;

            for (Document prod : prods)
            {
                String nameSwe = prod.getString("nameSwe");
                String nameEng = prod.getString("nameEng");
                double priceSEK = prod.getDouble("priceSEK");
                double priceGBP = prod.getDouble("priceGBP");
                double priceUSD = prod.getDouble("priceUSD");
                String unit = prod.getString("unitType");
                int volume = prod.getInteger("volume");

                p = new Product(nameSwe, nameEng, priceSEK, priceGBP, priceUSD, unit, volume);
                productList.add(p);
            }
        }
        return productList;
    }

    public LinkedList<Product> getSalesOnZipCode(String zip, Location location)
    {
        return null;
    }

    public LinkedList<Product> getSalesOnOccupation(String occupation)
    {
        return null;
    }

    public boolean addProductToStock(Location location, StockItem stockItem)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("stock");
        UpdateOptions options = new UpdateOptions();
        options.upsert(true);

//        collection.updateOne(eq("location", location.name() ), new Document("$push", new Document("Products", new Document("nameSwe", product.getNameSwe())
//                .append("name_eng", product.getNameEng())
//                .append("name_swe", product.getNameSwe())
//                .append("unit_type", product.getUnitType())
//                .append("volume", product.getVolume())
//                .append("units_in_stock", product.getUnits()))),options);

        collection.updateOne(eq("location", location.name()), new Document("$push", new Document("product", stockItem)), options);


        return true;
    }

    public boolean editStockQuantity(Product product, Location location){
        MongoCollection<Document> collection = mongoDb.getCollection("stock");
        Document stocks = collection.find(eq("location", location.name())).first();
        List<Document> products = (List<Document>) stocks.get("Products");
        for(Document doc : products){
            String name = doc.getString("nameSwe");
            if(name.equals(product.getNameSwe())){
                
            }
        }
        System.out.println();
        return true;
    }
    public boolean createStock(Location location)
    {
        MongoCollection<Document> collection = mongoDb.getCollection("stock");

        Document doc = new Document("location", location.name());

        collection.insertOne(doc);

        return true;
    }

    public void addFlavourToStock(Location location, Flavour f) {

    }
}
