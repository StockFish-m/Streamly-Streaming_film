/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ADMIN
 */

import java.sql.*;
import java.util.*;
import model.subscription.SubscriptionPlan;

public class SubscriptionPlanDAO {
    public List<SubscriptionPlan> getAllPlans() {
        List<SubscriptionPlan> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM ViewerSubscriptionPlan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubscriptionPlan plan = new SubscriptionPlan(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("cost"),
                    rs.getInt("base_duration")
                );
                list.add(plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public SubscriptionPlan getPlanById(int id) {
        String sql = "SELECT * FROM ViewerSubscriptionPlan WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SubscriptionPlan p = new SubscriptionPlan();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setCost(rs.getDouble("cost"));
                p.setBaseDuration(rs.getInt("base_duration"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

