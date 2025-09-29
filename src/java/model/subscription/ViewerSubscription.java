package model.subscription;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author MinhooMinh
 */
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ViewerSubscription {

    private int id;
    private int viewerId;
    private int planId;
    private String planName;
    private double cost;  // Store actual cost at time of purchase
    private LocalDate purchaseDate;
    private int duration; // Custom duration (not always same as plan)
    private LocalDate expiryDate;

    // Constructors
    public ViewerSubscription() {
    }

    public ViewerSubscription(int id, int viewerId, int planId, String planName, double cost,
            LocalDate purchaseDate, int duration, LocalDate expiryDate) {
        this.id = id;
        this.viewerId = viewerId;
        this.planId = planId;
        this.planName = planName;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.duration = duration;
        this.expiryDate = expiryDate;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViewerId() {
        return viewerId;
    }

    public void setViewerId(int viewerId) {
        this.viewerId = viewerId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return LocalDate.now().isBefore(expiryDate) || LocalDate.now().isEqual(expiryDate);
    }

    public boolean isForever() {
        return duration == -1 || expiryDate == null;
    }

    public Date getExpiryDateAsDate() {
        if (expiryDate == null) {
            return null;
        }
        return Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
