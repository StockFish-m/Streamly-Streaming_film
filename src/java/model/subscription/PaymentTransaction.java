package model.subscription;

import java.sql.Timestamp;

public class PaymentTransaction {
    private int id;
    private int userId;
    private int planId;
    private String txnRef;
    private double amount;
    private String status;
    private String responseCode;
    private Timestamp createdAt;

    // Constructors
    public PaymentTransaction() {}

    public PaymentTransaction(int userId, int planId, String txnRef, double amount, String status) {
        this.userId = userId;
        this.planId = planId;
        this.txnRef = txnRef;
        this.amount = amount;
        this.status = status;
    }
    private SubscriptionPlan plan;

    public SubscriptionPlan getPlan() {
    return plan;
}

public void setPlan(SubscriptionPlan plan) {
    this.plan = plan;
}
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    
}
