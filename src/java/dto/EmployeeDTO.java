/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author MinhooMinh
 */
import java.util.Date;
import java.util.List;

public class EmployeeDTO extends UserDTO {
    private List<String> roles;

    public EmployeeDTO() {}

    public EmployeeDTO(String id, String username, String email, Date createdAt, List<String> roles) {
        super(id, username, email, createdAt);
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
