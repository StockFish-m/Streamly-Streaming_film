/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.user.UserReview;

/**
 *
 * @author DELL
 */
public interface UserReviewDAO {
    void insertReview(UserReview review);
    void updateReview(UserReview review);
    UserReview getReviewByUserAndContent(int userId, int contentId);
    List<UserReview> getReviewsByContent(int contentId);
    double getAverageRatingByContentId(int contentId);
    
}
