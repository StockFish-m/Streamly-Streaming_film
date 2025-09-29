package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Date;
import model.user.Viewer;

public class UserSignupServlet extends HttpServlet {

    private String hashPassword(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        
        UserDAO dao = new UserDAOImpl();

        if (!phone.matches("\\d+")) {
            req.setAttribute("error", "Phone number must be digits only.");
        } else if (!email.contains("@")) {
            req.setAttribute("error", "Email must contain '@'.");
        } else if (dao.isUsernameTaken(username)) {
            req.setAttribute("error", "Tên đăng nhập đã tồn tại.");
        } else if (dao.isEmailTaken(email)) {
            req.setAttribute("error", "Email đã được sử dụng.");
        }

        if (req.getAttribute("error") != null) {
            req.setAttribute("enteredUsername", username);
            req.setAttribute("enteredEmail", email);
            req.setAttribute("enteredPhone", phone);
            req.setAttribute("enteredFullname", fullname);
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            return;
        }

        String hashedPassword = hashPassword(password);

        Viewer viewer = new Viewer();
        viewer.setUsername(username);
        viewer.setFullName(fullname);
        viewer.setEmail(email);
        viewer.setPhoneNumber(phone);
        viewer.setPassword_hash(hashedPassword);
        viewer.setCreated_at(new Date());

        dao.addViewer(viewer);

        HttpSession session = req.getSession();
        session.setAttribute("newUser", viewer);

        System.out.println(">>> Signup username: " + username);
        System.out.println(">>> Raw password: " + password);
        System.out.println(">>> Hashed password: " + hashedPassword);

        resp.sendRedirect("userlogin.jsp?success=1");
        //resp.sendRedirect(req.getContextPath() + "/userlogin.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/signup.jsp");
    }
}
