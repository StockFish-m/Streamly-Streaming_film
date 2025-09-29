/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Date;

/**
 *
 * @author MinhooMinh
 */
public class ViewerDTO extends UserDTO {
    private String activeSubscriptionName;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;

    public ViewerDTO() {}

    public ViewerDTO(String id, String username, String email, Date createdAt,
                     String activeSubscriptionName, Date subscriptionStartDate, Date subscriptionEndDate) {
        super(id, username, email, createdAt);
        this.activeSubscriptionName = activeSubscriptionName;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getActiveSubscriptionName() {
        return activeSubscriptionName;
    }

    public void setActiveSubscriptionName(String activeSubscriptionName) {
        this.activeSubscriptionName = activeSubscriptionName;
    }

    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }
}
