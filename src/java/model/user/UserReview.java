/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.util.Date;


public class UserReview {
    private int userId;
    private int contentId;
    private int rating;
    private String comment;
    private String fullname;
    private Date reviewDate;

    public UserReview() {
    }
    
    

    public UserReview(int userId, int contentId, int rating, String comment, String fullname, Date reviewDate) {
        this.userId = userId;
        this.contentId = contentId;
        this.rating = rating;
        this.comment = comment;
        this.fullname =fullname;
        this.reviewDate = reviewDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    
}
