package database;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import domainEntities.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

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
                .append("price_sek", product.getPriceSEK())
                .append("price_gbp", product.getPriceGBP())
                .append("price_usd", product.getPriceUSD())
                .append("price_gbp", product.getPriceGBP())
                .append("unit", product.getUnitType())
                .append("volume", product.getVolume());
        collection.insertOne(doc);

        return true;
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

    //SSN hårdkodat
    public boolean addComment(Comment comment)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("employee");
        collection.updateOne(eq("ssn", "840309-****"), new Document("$push", new Document("comment", comment)));
        return true;
    }



}
