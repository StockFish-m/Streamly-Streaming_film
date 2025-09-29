package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.user.Viewer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;

@WebServlet("/changepassword")
public class ChangePasswordServlet extends HttpServlet {

    private final UserDAO dao = new UserDAOImpl();

    private String hashPassword(String input) {
        if (input == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Viewer viewer = (Viewer) session.getAttribute("user");
        Viewer resetViewer = (Viewer) session.getAttribute("reset_user");

        boolean isReset = (resetViewer != null && viewer == null);
        Viewer target = isReset ? resetViewer : viewer;

        if (target == null) {
            resp.sendRedirect("userlogin.jsp");
            return;
        }

        String oldPass = req.getParameter("old_password");
        String newPass = req.getParameter("new_password");
        String confirmPass = req.getParameter("confirm_password");

        if (newPass == null || confirmPass == null ||
            newPass.isEmpty() || confirmPass.isEmpty() ||
            (!isReset && (oldPass == null || oldPass.isEmpty()))) {

            req.setAttribute("error", "Please fill in all required fields.");
            req.getRequestDispatcher("changepassword.jsp").forward(req, resp);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            req.setAttribute("error", "New password and confirmation do not match.");
            req.getRequestDispatcher("changepassword.jsp").forward(req, resp);
            return;
        }

        boolean success = false;

        if (!isReset) {
            String hashedOld = hashPassword(oldPass);
            if (!target.getPassword_hash().equals(hashedOld)) {
                req.setAttribute("error", "Incorrect old password.");
                req.getRequestDispatcher("changepassword.jsp").forward(req, resp);
                return;
            }
        }

        target.setPassword_hash(hashPassword(newPass));
        success = dao.updateViewer(target);

        if (success) {
            if (isReset) {
                session.removeAttribute("reset_user");
                resp.sendRedirect(req.getContextPath() + "/userlogin.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
        } else {
            req.setAttribute("error", "Failed to update password. Please try again.");
            req.getRequestDispatcher("changepassword.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("changepassword.jsp").forward(req, resp);
    }
}
