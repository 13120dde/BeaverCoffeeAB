package database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import domainEntities.Comment;
import domainEntities.Customer;
import domainEntities.Employee;
import domainEntities.Product;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDb {

    private MongoClient mongoClient;
    private MongoDatabase mongoDb;
    private CodecRegistry pojoCodecRegistry;

    public MongoDb() throws UnknownHostException {
        mongoClient = new MongoClient();
        mongoDb = mongoClient.getDatabase("BeaverCoffee");
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

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

    public boolean updateEmployee(Employee employee)
    {
        MongoCollection <Document> collection = mongoDb.getCollection("employee");
        Bson filter = new Document("ssn", new ObjectId(employee.getIdNumber()));
        Bson newValue = new Document("name", employee.getName())
                .append("ssn", employee.getIdNumber())
                .append("position", employee.getPosition())
                .append("start_date", employee.getStartDate())
                .append("end_date", employee.getEndDate())
                .append("location", employee.getLocation().name())
                .append("service_grade", employee.getServiceGrade())
                .append("comments","" );
        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);

        return true;
    }

    public boolean addComment(Comment comment)
    {
        int employeeId = comment.getEmployeeId();
//        Filters filter = new Filters()

//        Bson filter = new Document("_id", "Harish Taware");
//        Bson newValue = new Document("salary", 90000);
//        Bson updateOperationDocument = new Document("$set", newValue);
//        collection.updateOne(filter, updateOperationDocument);
//
        return true;
    }


}
