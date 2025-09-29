/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class User {
    private int user_id;
    private String username;
    private String fullname;
    private String phone_number;
    private String email;
    private String password_hash;
    private Date created_at;

    public User() {
    }

    public User(int user_id, String username, String fullname, String phone_number, String email, String password_hash, Date created_at) {
        this.user_id = user_id;
        this.username = username;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.email = email;
        this.password_hash = password_hash;
        this.created_at = created_at;
    }

    
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
  
} 
   

   