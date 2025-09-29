//package servlet;
//
//import dao.UserDAO;
//import dao.UserDAOImpl;
//import model.user.Viewer;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//import java.security.MessageDigest;
//
//@WebServlet("/resetpassword")
//public class ResetPasswordServlet extends HttpServlet {
//
//    private final UserDAO dao = new UserDAOImpl();
//
//    private String hashPassword(String input) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hashed = md.digest(input.getBytes());
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hashed) sb.append(String.format("%02x", b));
//            return sb.toString();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        int userId = Integer.parseInt(req.getParameter("user_id"));
//        String newPassword = req.getParameter("new_password");
//
//        Viewer viewer = dao.getViewer(userId);
//        viewer.setPassword_hash(hashPassword(newPassword));
//
//        boolean updated = dao.updateViewer(viewer);
//
//        if (updated) {
//            req.setAttribute("message", "Password reset successfully!");
//            req.getRequestDispatcher("userlogin.jsp").forward(req, resp);
//        } else {
//            req.setAttribute("error", "Failed to reset password.");
//            req.getRequestDispatcher("resetpassword.jsp").forward(req, resp);
//        }
//    }
//}
