/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;


public enum EmployeeRole {
    MOVIE_EDITOR(1, "Editor for Movies"),
    CUSTOMER_SUPPORT(2, "Customer Management"),
    EMPLOYEE_MANAGER(3, "Employee Management"),
    CONTENT_MANAGER(4, "Content & Publishing"),
    ANALYST(5, "Data & Metrics");

    private final int id;
    private final String description;

    EmployeeRole(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static EmployeeRole fromId(int id) {
        for (EmployeeRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown EmployeeRole ID: " + id);
    }
}
