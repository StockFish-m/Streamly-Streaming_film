/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.*;
import model.user.UserReview;

public class UserReviewDAOImpl implements UserReviewDAO {

    @Override
    public void insertReview(UserReview review) {
        String sql = "INSERT INTO UserReview (user_id, content_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getContentId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setDate(5, new java.sql.Date(review.getReviewDate().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReview(UserReview review) {
        String sql = "UPDATE UserReview SET rating = ?, comment = ?, review_date = ? WHERE user_id = ? AND content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setDate(3, new java.sql.Date(review.getReviewDate().getTime()));
            ps.setInt(4, review.getUserId());
            ps.setInt(5, review.getContentId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserReview getReviewByUserAndContent(int userId, int contentId) {
        String sql = "SELECT r.*, u.fullname FROM UserReview r JOIN Viewer u ON r.user_id = u.user_id WHERE r.user_id = ? AND r.content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, contentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserReview review = new UserReview();
                    review.setUserId(rs.getInt("user_id"));
                    review.setContentId(rs.getInt("content_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setReviewDate(rs.getDate("review_date"));
                    review.setFullname(rs.getString("fullname"));
                    return review;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserReview> getReviewsByContent(int contentId) {
        List<UserReview> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.fullname FROM UserReview r JOIN Viewer u ON r.user_id = u.user_id WHERE r.content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserReview review = new UserReview();
                    review.setUserId(rs.getInt("user_id"));
                    review.setContentId(rs.getInt("content_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setReviewDate(rs.getDate("review_date"));
                    review.setFullname(rs.getString("fullname"));
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public double getAverageRatingByContentId(int contentId) {
        String sql = "SELECT AVG(CAST(rating AS FLOAT)) AS avg_rating FROM UserReview WHERE content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Return 0 if no reviews found or error occurs
    }

}
