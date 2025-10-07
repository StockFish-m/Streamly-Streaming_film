/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.subscription;

import java.util.Date;

public class SubscriptionPlan {

    private  int id;
    private  String name;
    private  double cost;
    private  int baseDuration;

    public SubscriptionPlan() {
    }
    

    
    public SubscriptionPlan(int id, String name, double cost, int baseDuration) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.baseDuration = baseDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getBaseDuration() {
        return baseDuration;
    }

    public void setBaseDuration(int baseDuration) {
        this.baseDuration = baseDuration;
    }

    
//    public Date getExpiryDateAsDate() {
//        if ( == null) return null;
//        return Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//    }
    
}
