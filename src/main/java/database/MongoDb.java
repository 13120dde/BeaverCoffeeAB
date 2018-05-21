package database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import domainEntities.Employee;
import domainEntities.Product;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Arrays;

public class MongoDb {

    private MongoClient mongoClient;
    private MongoDatabase mongoDb;

    public MongoDb() throws UnknownHostException {
        mongoClient = new MongoClient();
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
                .append("price_gbp", product.getPriceGBP())
                .append("unit", product.getUnitType())
                .append("volume", product.getVolume());
        collection.insertOne(doc);

        return true;
    }
}
