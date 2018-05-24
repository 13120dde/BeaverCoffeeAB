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

public class MongoDb {

    private MongoClient mongoClient;
    private MongoDatabase mongoDb;
    private CodecRegistry pojoCodecRegistry;

    public MongoDb() throws UnknownHostException {
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        mongoDb = mongoClient.getDatabase("BeaverCoffee");


    }

    public Employee login(String userName, String password) {
        return null;
    }

    public boolean addProduct(Product product)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("product");
        Document doc = new Document("name_swe", product.getNameSwe())
                .append("name_eng", product.getNameEng())
                .append("price_sek", product.getPriceSEK())
                .append("price_gbp", product.getPriceGBP())
                .append("price_usd", product.getPriceUSD())
                .append("unit", product.getUnitType())
                .append("volume", product.getVolume())
                .append("eligible_discount",product.isEligibleForDiscount())
                .append("flavour_enabled", product.isFlavorEnabled());
        collection.insertOne(doc);

        return true;
    }

    public LinkedList<Product> getAvailableProducts(){
        MongoCollection<Document> collection = mongoDb.getCollection("product");
        List<Document> documents = collection.find().into(new LinkedList<Document>());
        List<Product> availableProducts = new LinkedList<Product>();
        for(Document document : documents){
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
        MongoCollection <Document> collection = mongoDb.getCollection("employee");
        Document doc = new Document("name", employee.getName())
                .append("ssn", employee.getIdNumber())
                .append("position", employee.getLocation().name())
                .append("start_date", employee.getStartDate())
                .append("end_date", employee.getEndDate())
                .append("location", employee.getLocation().name())
                .append("service_grade", employee.getServiceGrade())
                .append("comments","" );
        collection.insertOne(doc);


        return true;
    }

    //TODO Behöver skilja på brewed och bagged coffee för gratis kaffe samt räkna antal
    //TODO Order in i customer?
    public boolean addOrder(Order order)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("order");
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
        MongoCollection <Document> collection = mongoDb.getCollection("customer");
        Document doc = new Document("name", customer.getName())
                .append("ssn", customer.getIdNumber())
                .append("barcode", customer.getBarcode())
                .append("occupation", customer.getOccupation())
                .append("date", customer.getRegisteredDate())
                .append("counter", 0)
                .append("date", customer.getRegisteredDate())
                .append("address", customer.getAddress() );
        collection.insertOne(doc);


        return true;
    }

    public boolean updateCustomer (Customer customer)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("customer");
        Bson filter = new Document("ssn", customer.getIdNumber());
        Bson newValue = new Document("name", customer.getName())
                .append("ssn", customer.getIdNumber())
                .append("barcode", customer.getBarcode())
                .append("occupation", customer.getOccupation())
                .append("date", customer.getRegisteredDate())
                .append("counter", customer.getTotalPurchases())
                .append("date", customer.getRegisteredDate())
                .append("address", customer.getAddress() );

        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);

        return true;
    }
    //TODO int som parameter för antar drycker köpta
    public boolean increasePurchases(String barcode)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("customer");
        Bson filter = new Document("barcode", barcode);
        Bson newValue = new Document("counter",1 );
        Bson updateOperationDocument = new Document("$inc", newValue);
        collection.updateOne(filter, updateOperationDocument);

        return true;
    }

    public boolean updateEmployee(Employee employee)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("employee");
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
        MongoCollection <Document> collection = mongoDb.getCollection("customer");
        MongoCursor<Document> cursor = collection.find(new Document("date", new Document("$gte", from)
                .append("$lt", to))).iterator();



        if (cursor.hasNext())
            System.out.println("Träff");

        else
            System.out.println("NAJ");

        List <Customer> customerList = new ArrayList<Customer>();
        try {
            while (cursor.hasNext()) {

                Document d = cursor.next();
                String name = d.getString("name");
                String occupation = d.getString("occupation");
                String barcode = d.getString("barcode");
                String idNumber = d.getString("ssn");
                Date registered = d.getDate("date");

                Object address = d.get("address");
                Document addressDoc = (Document)address;
                String city = addressDoc.getString("street");
                String location = addressDoc.getString("location");
                String street = addressDoc.getString("street");
                String zip = addressDoc.getString("zip");

//                List a = new ArrayList();
//
//                Product p = new Product("Kaffe", "Coffee", 10, 2, 3,
//                        "l", 2);
//
//                Product p2 = new Product("Sko", "Shoe", 10, 2, 3,
//                        "kg", 6);
//
//                a.add(p);
//                a.add(p2);
//
//                Order o = new Order(12, "12345", 432, new Date(), Location.SWEDEN, a);
//
//                List <Order> l2 = new ArrayList();
//                l2.add(o);

                Location l = Enum.valueOf(Location.class, location);
                Address ad = new Address(street, zip, city,l );
                Customer c = new Customer(name, occupation, barcode,idNumber, ad, registered  );
                customerList.add(c);

//                Customer c = new Customer()

//                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        return customerList;
    }

    //SSN hårdkodat
    public boolean addComment(Comment comment)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("employee");
        collection.updateOne(eq("ssn", "840309-****"), new Document("$push", new Document("comment", comment)));
        return true;
    }


    public boolean addFlavour(Flavour f) {
        MongoCollection <Document> collection = mongoDb.getCollection("flavour");
        Document doc = new Document("name_swe", f.getNameSwe())
                .append("name_eng", f.getNameEng())
                .append("unit", f.getUnit())
                .append("volume", f.getVolume());
        collection.insertOne(doc);

        return true;
    }

    public List<Flavour> getAvailableFlavours() {
        MongoCollection<Document> collection = mongoDb.getCollection("flavour");
        List<Document> documents = collection.find().into(new LinkedList<Document>());
        List<Flavour> availableFlavours = new LinkedList<Flavour>();
        for(Document document : documents){
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
