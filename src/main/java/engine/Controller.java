package engine;

import database.MongoDb;
import stakeholders.EmployePosition;
import stakeholders.Employee;

public class Controller {

    private MongoDb database;
    private Employee employee;

    public Controller(MongoDb database) {
        this.database=database;
    }

    public boolean login(String userName, String password) {
       employee = new Employee();
        // employee = database.login(userName,password);
        /*if(employee==null)
            return false;
        */return true;
    }

    public EmployePosition getCurrentUserPosition() {
        return employee.getPosition();
    }

    public void logout() {
        employee=null;
    }
}
