package database;

import com.mongodb.MongoClient;
import stakeholders.Employee;

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
