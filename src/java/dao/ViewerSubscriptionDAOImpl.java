package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import model.subscription.ViewerSubscription;


public class ViewerSubscriptionDAOImpl {

    public void subscribe(int viewerId, int planId) throws SQLException {
    String sql = """
        INSERT INTO UserSubscription (user_id, plan_id, purchase_date, duration)
        SELECT ?, id, ?, base_duration
        FROM ViewerSubscriptionPlan
        WHERE id = ?
    """;

    LocalDate today = LocalDate.now();

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, viewerId);
        ps.setDate(2, Date.valueOf(today)); // purchase_date
        ps.setInt(3, planId);               // plan id in WHERE clause

        ps.executeUpdate();
        System.out.println("✅ Đăng ký thành công: viewerId=" + viewerId + ", planId=" + planId);

    } catch (SQLException e) {
        System.err.println("❌ SQL Error in subscribe: " + e.getMessage());
        throw e;
    }
}


    // Cũng nên có thêm phương thức getLatestByViewerId nếu servlet gọi
    public ViewerSubscription getLatestByViewerId(int viewerId) throws SQLException {
        String sql = """
            SELECT TOP 1 us.*, vsp.name, vsp.cost
            FROM UserSubscription us
            JOIN ViewerSubscriptionPlan vsp ON us.plan_id = vsp.id
            WHERE user_id = ?
            ORDER BY us.purchase_date DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, viewerId);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    ViewerSubscription vs = new ViewerSubscription();
                    vs.setId(rs.getInt("id"));
                    vs.setViewerId(rs.getInt("user_id"));
                    vs.setPlanId(rs.getInt("plan_id"));
                    vs.setPlanName(rs.getString("name"));
                    vs.setCost(rs.getDouble("cost"));
                    vs.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
                    vs.setDuration(rs.getInt("duration"));
                    vs.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
                    return vs;
                }
            }
        }
        return null;
    }
}
