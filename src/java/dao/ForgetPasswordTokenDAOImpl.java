/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.user.ForgetPasswordToken;

/**
 *
 * @author HP
 */
public class ForgetPasswordTokenDAOImpl implements ForgetPasswordTokenDAO {
    
     public String getFormatDate(LocalDateTime myDateObj) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        String formattedDate = myDateObj.format(myFormatObj);  
        return formattedDate;
     }
              

    public boolean insertTokenForget(ForgetPasswordToken tokenForget) {
        String sql = "INSERT INTO ForgetPasswordToken ([token],[expiryTime],[isUsed],[userId]) VALUES(?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tokenForget.getToken());
            // Chuyển trực tiếp LocalDateTime sang Timestamp để giảm lỗi định dạng
            ps.setTimestamp(2, Timestamp.valueOf(tokenForget.getExpiryTime()));
            ps.setBoolean(3, tokenForget.isIsUsed());
            ps.setInt(4, tokenForget.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // In ra stack trace để dễ debug
            e.printStackTrace();
            
        }
        return false;
    }
    public ForgetPasswordToken getTokenPassword(String token) {
        String sql = "Select * from ForgetPasswordToken where token = ?";
        try {
            PreparedStatement st = DBConnection.getConnection().prepareStatement(sql);
            st.setString(1, token);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new ForgetPasswordToken(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getBoolean("isUsed"),
                        rs.getString("token"),
                        rs.getTimestamp("expiryTime").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void updateStatus(ForgetPasswordToken token) {
        System.out.println("token = "+token);
        String sql = "UPDATE ForgetPasswordToken SET [isUsed] = ? WHERE token = ?";
        try {
            PreparedStatement st = DBConnection.getConnection().prepareStatement(sql);
            st.setBoolean(1, token.isIsUsed());
            st.setString(2, token.getToken());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
