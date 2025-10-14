/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;


public class ProgressDAOImpl implements ProgressDAO {

    @Override
    public void saveProgress(int userId, int contentId, double seconds) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "MERGE INTO [dbo].[WatchProgress] AS target "
                    + "USING (SELECT ? AS user_id, ? AS content_id) AS source "
                    + "ON target.user_id = source.user_id AND target.content_id = source.content_id "
                    + "WHEN MATCHED THEN "
                    + "  UPDATE SET watched_seconds = ? "
                    + "WHEN NOT MATCHED THEN "
                    + "  INSERT (user_id, content_id, watched_seconds) VALUES (?, ?, ?);";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setInt(2, contentId);
                ps.setDouble(3, seconds);
                ps.setInt(4, userId);
                ps.setInt(5, contentId);
                ps.setDouble(6, seconds);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getProgress(int userId, int contentId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT watched_seconds FROM [dbo].[WatchProgress] WHERE user_id = ? AND content_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setInt(2, contentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getDouble("watched_seconds");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}
