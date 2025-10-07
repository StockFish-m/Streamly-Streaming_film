/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;



import model.subscription.PaymentTransaction;
import java.sql.*;
import java.time.LocalDate;

public class ViewerSubscriptionDAO {

    //  Tạo subscription mới cho user khi thanh toán thành công
    public void createSubscriptionFromTransaction(PaymentTransaction tx) {
        String sql = "INSERT INTO UserSubscription (user_id, plan_id, duration)VALUES ( ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tx.getUserId());
            ps.setInt(2, tx.getPlanId());
            ps.setInt(3, tx.getPlan().getBaseDuration());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
