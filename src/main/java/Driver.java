import database.MongoDb;
import engine.Controller;
import userInterface.BeaverCLI;

import java.net.UnknownHostException;

public class Driver {

    public static void main(String[] args) {
        try {
            MongoDb database = new MongoDb();
            Controller controller = new Controller(database);
            BeaverCLI cli = new BeaverCLI(controller);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
