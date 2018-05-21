package database;

import com.mongodb.MongoClient;
import domainEntities.Employee;

import java.net.UnknownHostException;

public class MongoDb {

    private MongoClient mongoClient;
    public MongoDb() throws UnknownHostException {
        mongoClient = new MongoClient();

    }

    public Employee login(String userName, String password) {
        return null;
    }
}
