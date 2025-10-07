/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.subscription.ViewerSubscription;
import model.user.Employee;
import model.user.EmployeeRole;
import model.user.User;
import model.user.Viewer;


public class UserDAOImpl implements UserDAO {

    private static final String SELECT_VIEWER_BY_USERNAME = "SELECT * FROM Viewer WHERE username = ? AND password_hash = ?";
    private static final String SELECT_EMPLOYEE_BY_USERNAME = "SELECT * FROM Employee WHERE username = ? AND password_hash = ?";
    private static final String SELECT_VIEWER_BY_ID = "SELECT * FROM Viewer WHERE user_id = ?";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM Employee WHERE id = ?";
    private static final String SELECT_ALL_VIEWERS = "SELECT * FROM Viewer";
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM Employee";

    private static final String SELECT_EMPLOYEE_ROLES = "SELECT r.* FROM EmployeeRoleAssignment a "
            + "JOIN EmployeeRole r ON a.role_id = r.id WHERE a.employee_id = ?";

    @Override
    public Viewer getViewer(String username, String password) {
        Viewer viewer = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_VIEWER_BY_USERNAME)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                viewer = mapViewerWithSubscription(rs, conn);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return viewer;
    }

    public Employee getEmployee(String username, String password) {
        Employee e = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_EMPLOYEE_BY_USERNAME)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e = mapResultSetToEmployee(rs);

                // Fetch roles for this employee
                List<EmployeeRole> roles = new ArrayList<>();
                String roleSql = "SELECT a.role_id FROM EmployeeRoleAssignment a WHERE a.employee_id = ?";

                try (PreparedStatement roleStmt = conn.prepareStatement(roleSql)) {
                    roleStmt.setInt(1, e.getUser_id());
                    ResultSet roleRs = roleStmt.executeQuery();

                    while (roleRs.next()) {
                        int roleId = roleRs.getInt("role_id");
                        try {
                            EmployeeRole role = EmployeeRole.fromId(roleId);
                            roles.add(role);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.WARNING, "Unknown role_id: " + roleId, ex);
                        }
                    }

                    e.setRoles(roles);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return e;
    }

    @Override
    public Viewer getViewer(int userId) {
        Viewer viewer = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_VIEWER_BY_ID)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                viewer = mapViewerWithSubscription(rs, conn);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return viewer;
    }

    @Override
    public Employee getEmployee(int userId) {
        Employee e = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e = mapResultSetToEmployee(rs);

                // Step 2: Fetch roles for this employee
                List<EmployeeRole> roles = new ArrayList<>();
                String roleSql = "SELECT role_id FROM EmployeeRoleAssignment WHERE employee_id = ?";

                try (PreparedStatement roleStmt = conn.prepareStatement(roleSql)) {
                    roleStmt.setInt(1, e.getUser_id());  // assuming getUserId() returns a String
                    ResultSet roleRs = roleStmt.executeQuery();

                    while (roleRs.next()) {
                        int roleId = roleRs.getInt("role_id");
                        try {
                            EmployeeRole role = EmployeeRole.fromId(roleId);
                            roles.add(role);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.WARNING,
                                    "Unknown role_id: " + roleId, ex);
                        }
                    }

                    e.setRoles(roles);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return e;
    }

    @Override
    //get all Employee
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_EMPLOYEES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    //get all Viewer
    public List<Viewer> getAllViewers() {
        List<Viewer> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_VIEWERS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Viewer viewer = mapResultSetToViewer(rs);
                list.add(viewer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addViewer(Viewer viewer) {
        String sql = "INSERT INTO Viewer (username, email, password_hash, created_at, fullname, phone_number) "
                + "VALUES (?, ?, ?, ?, ?, ?)";  // ❌ Bỏ cột user_id

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, viewer.getUsername());
            ps.setString(2, viewer.getEmail());
            ps.setString(3, viewer.getPassword_hash());
            ps.setDate(4, new java.sql.Date(viewer.getCreated_at().getTime()));
            ps.setString(5, viewer.getFullName());
            ps.setString(6, viewer.getPhoneNumber());

            ps.executeUpdate();

            // Lấy user_id được sinh ra tự động
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    viewer.setUser_id(rs.getInt(1)); // Gán lại user_id nếu cần dùng sau này
                }
            }

            System.out.println(">>> Viewer inserted with username: " + viewer.getUsername());

        } catch (SQLException e) {
            System.err.println(">>> Error inserting viewer:");
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateViewer(Viewer viewer) {
        String sql = "UPDATE Viewer SET username = ?, email = ?, password_hash = ?, created_at = ?, fullname = ?, phone_number = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, viewer.getUsername());
            ps.setString(2, viewer.getEmail());
            ps.setString(3, viewer.getPassword_hash());
            ps.setDate(4, new java.sql.Date(viewer.getCreated_at().getTime()));
            ps.setString(5, viewer.getFullName());
            ps.setString(6, viewer.getPhoneNumber());
            ps.setInt(7, viewer.getUser_id());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("fullname"),
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getDate("created_at")
        );
    }

    public Viewer mapResultSetToViewer(ResultSet rs) throws SQLException {
        return new Viewer(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("fullname"),
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getDate("created_at")
        );
    }

    @Override
    public boolean updateViewerProfile(Viewer viewer) {
        String sql = "UPDATE Viewer SET  email = ?, fullname = ?, phone_number = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, viewer.getEmail());
            ps.setString(2, viewer.getFullName());
            ps.setString(3, viewer.getPhoneNumber());
            ps.setInt(4, viewer.getUser_id());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Viewer getViewerByEmail(String email) {
        Viewer viewer = null;
        String sql = "SELECT * FROM Viewer WHERE email = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                viewer = mapResultSetToViewer(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewer;
    }

    @Override
    public boolean updatePassword(String email, String newPasswordHash) {
        String sql = "UPDATE Viewer SET password_hash = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hashPassword(newPasswordHash));
            ps.setString(2, email);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private String hashPassword(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Viewer mapViewerWithSubscription(ResultSet rs, Connection conn) throws SQLException {
        Viewer viewer = mapResultSetToViewer(rs);

        String subSql = """
        SELECT TOP (1) id, user_id, plan_id, purchase_date, duration, expiry_date
        FROM UserSubscription
        WHERE user_id = ?
        ORDER BY purchase_date DESC
    """;

        try (PreparedStatement subStmt = conn.prepareStatement(subSql)) {
            subStmt.setInt(1, viewer.getUser_id());
            ResultSet subRs = subStmt.executeQuery();

            if (subRs.next()) {
                ViewerSubscription subscription = new ViewerSubscription();
                subscription.setId(subRs.getInt("id"));
                subscription.setViewerId(subRs.getInt("user_id"));
                subscription.setPlanId(subRs.getInt("plan_id"));
                subscription.setPurchaseDate(subRs.getDate("purchase_date").toLocalDate());

                int duration = subRs.getInt("duration");
                if (subRs.wasNull()) {
                    duration = -1;
                }
                subscription.setDuration(duration);

                Date expiryDateSql = subRs.getDate("expiry_date");
                if (expiryDateSql != null) {
                    subscription.setExpiryDate(expiryDateSql.toLocalDate());
                } else {
                    subscription.setExpiryDate(null); // represent "forever"
                }

                viewer.setActiveSubscription(subscription);
            }

        }

        return viewer;
    }
    
    @Override
public boolean isUsernameTaken(String username) {
    String sql = "SELECT 1 FROM Viewer WHERE username = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Có ít nhất 1 dòng => đã tồn tại
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

@Override
public boolean isEmailTaken(String email) {
    String sql = "SELECT 1 FROM Viewer WHERE email = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Có ít nhất 1 dòng => đã tồn tại
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
