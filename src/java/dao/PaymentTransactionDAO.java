package dao;

import java.sql.*;
import model.subscription.PaymentTransaction;
import model.subscription.SubscriptionPlan;

public class PaymentTransactionDAO {

    // Thêm giao dịch mới với trạng thái PENDING
    public void insert(PaymentTransaction tx) {
        String sql = "INSERT INTO PaymentTransaction (user_id, plan_id, txn_ref, amount, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tx.getUserId());
            ps.setInt(2, tx.getPlanId());
            ps.setString(3, tx.getTxnRef());
            ps.setDouble(4, tx.getAmount());
            ps.setString(5, tx.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật trạng thái giao dịch
    public void updateStatus(String txnRef, String newStatus, String responseCode) {
        String sql = "UPDATE PaymentTransaction SET status = ?, response_code = ? WHERE txn_ref = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, responseCode);
            ps.setString(3, txnRef);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy giao dịch theo txn_ref
    public PaymentTransaction getByTxnRef(String txnRef) {
        String sql
                = "SELECT pt.*, p.base_duration FROM PaymentTransaction pt "
                + "JOIN ViewerSubscriptionPlan p ON pt.plan_id = p.id WHERE pt.txn_ref = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txnRef);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PaymentTransaction tx = new PaymentTransaction();
                tx.setId(rs.getInt("id"));
                tx.setUserId(rs.getInt("user_id"));
                tx.setPlanId(rs.getInt("plan_id"));
                tx.setTxnRef(rs.getString("txn_ref"));
                tx.setAmount(rs.getDouble("amount"));
                tx.setStatus(rs.getString("status"));
                tx.setResponseCode(rs.getString("response_code"));
                tx.setCreatedAt(rs.getTimestamp("created_at"));

                SubscriptionPlan plan = new SubscriptionPlan();

                plan.setBaseDuration(rs.getInt("base_duration"));
                tx.setPlan(plan);
                return tx;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
