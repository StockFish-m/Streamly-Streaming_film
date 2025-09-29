package dao;

import model.user.Employee;
import model.user.EmployeeRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String SELECT_EMPLOYEE_BY_CREDENTIALS =
            "SELECT * FROM Employee WHERE (username = ? OR email = ?) AND password_hash = ?";
    private static final String SELECT_EMPLOYEE_BY_ID =
            "SELECT * FROM Employee WHERE id = ?";
    private static final String SELECT_ALL_EMPLOYEES =
            "SELECT * FROM Employee";
    private static final String INSERT_EMPLOYEE =
            "INSERT INTO Employee (username, email, fullname, phone_number, password_hash, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE =
            "UPDATE Employee SET username = ?, email = ?, fullname = ?, phone_number = ?, password_hash = ?, created_at = ? WHERE id = ?";
    private static final String INSERT_ROLE_ASSIGNMENT =
            "INSERT INTO EmployeeRoleAssignment (employee_id, role_id) VALUES (?, ?)";
    private static final String DELETE_ROLE_ASSIGNMENT =
            "DELETE FROM EmployeeRoleAssignment WHERE employee_id = ? AND role_id = ?";

    @Override
    public Employee login(String usernameOrEmail, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EMPLOYEE_BY_CREDENTIALS)) {

            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = mapEmployee(rs);
                emp.setRoles(getRolesForEmployee(emp.getUser_id(), conn));
                return emp;
            }

        } catch (SQLException e) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    @Override
    public Employee getById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = mapEmployee(rs);
                emp.setRoles(getRolesForEmployee(id, conn));
                return emp;
            }

        } catch (SQLException e) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_EMPLOYEES)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee emp = mapEmployee(rs);
                emp.setRoles(getRolesForEmployee(emp.getUser_id(), conn));
                list.add(emp);
            }

        } catch (SQLException e) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    @Override
    public boolean insert(Employee e) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getUsername());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getFullName());            
            ps.setString(4, e.getPhoneNumber());       
            ps.setString(5, e.getPassword_hash());        
            ps.setTimestamp(6, new Timestamp(e.getCreated_at().getTime()));  


            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        e.setUserId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean update(Employee e) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EMPLOYEE)) {

           ps.setString(1, e.getUsername());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getFullName());            
            ps.setString(4, e.getPhoneNumber());       
            ps.setString(5, e.getPassword_hash());        
            ps.setTimestamp(6, new Timestamp(e.getCreated_at().getTime()));
            ps.setInt(7, e.getUser_id());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean assignRole(int employeeId, int roleId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ROLE_ASSIGNMENT)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, roleId);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean removeRole(int employeeId, int roleId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ROLE_ASSIGNMENT)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, roleId);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("fullname").trim(),
                rs.getString("phone_number").trim(),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getTimestamp("created_at")
        );
    }

    private List<EmployeeRole> getRolesForEmployee(int employeeId, Connection conn) {
        List<EmployeeRole> roles = new ArrayList<>();
        String sql = "SELECT role_id FROM EmployeeRoleAssignment WHERE employee_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                try {
                    roles.add(EmployeeRole.fromId(roleId));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.WARNING,
                            "Unknown role id: " + roleId, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return roles;
    }
}
