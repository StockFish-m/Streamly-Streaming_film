/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.user.Employee;
import model.user.User;
import model.user.Viewer;


public interface UserDAO {

    //get user by username + password (for login)
    public Viewer getViewer(String username, String password);

    public Employee getEmployee(String username, String password);

    //get viewer by its id
    public Viewer getViewer(int viewerId);

    public Employee getEmployee(int empId);

    //get all User
    public List<Viewer> getAllViewers();

    public List<Employee> getAllEmployees();

    //add new viewer
    public void addViewer(Viewer user);

    //update an existing user
    public boolean updateViewer(Viewer user);

    // Get viewer by email for reset password
    public Viewer getViewerByEmail(String email);

    // get new password, update to DB
    public boolean updatePassword(String email, String newPasswordHash);

    // update viewer profile 
    public boolean updateViewerProfile(Viewer viewer);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);
}
