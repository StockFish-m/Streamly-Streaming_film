/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.util.Date;
import model.subscription.ViewerSubscription;


public class Viewer extends User {
    
    
    private ViewerSubscription activeSubscription;

    public Viewer() {
        super();
    }

    public Viewer(int userId, String username, String fullname, String phone_number, String email, String passwordHash, Date createdAt) {
        super(userId, username, fullname, phone_number, email, passwordHash, createdAt);
    }

    public ViewerSubscription getActiveSubscription() {
        return activeSubscription;
    }

    public void setActiveSubscription(ViewerSubscription activeSubscription) {
        this.activeSubscription = activeSubscription;
    }

//    public boolean hasActiveSubscription() {
//        return activeSubscription != null && activeSubscription.isActive();
//    }
}