/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Employee extends User {
    private List<EmployeeRole> roles;

    public Employee() {
        this.roles = new ArrayList<>();
    }

    public Employee(int userId, String username, String fullname, String phone_number,
                    String email, String passwordHash, Date createdAt) {
        super(userId, username, fullname, phone_number, email, passwordHash, createdAt);
        this.roles = new ArrayList<>();
    }

    public List<EmployeeRole> getRoles() {
        return roles;
    }

    public void setRoles(List<EmployeeRole> roles) {
        this.roles = roles;
    }

    public boolean hasRole(EmployeeRole role) {
        return roles != null && roles.contains(role);
    }

    // ✅ Bổ sung setter để tránh lỗi trong EmployeeDAOImpl
    public void setUserId(int userId) {
    // Gọi setter từ lớp cha (nếu có)
    super.setUser_id(userId);
}

}
