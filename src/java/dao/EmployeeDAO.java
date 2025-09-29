package dao;

import model.user.Employee;
import java.util.List;

public interface EmployeeDAO {


    Employee login(String usernameOrEmail, String password);

    Employee getById(int id);

    List<Employee> getAll();

    boolean insert(Employee employee);
    boolean update(Employee employee);
    boolean assignRole(int employeeId, int roleId);
    boolean removeRole(int employeeId, int roleId);
}
