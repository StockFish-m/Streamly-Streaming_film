package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.user.User;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

public class UserLoginServlet extends HttpServlet {

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
        String password = req.getParameter("password");
        String hashedPassword = hashPassword(password);
        String remember = req.getParameter("remember");

        UserDAO dao = new UserDAOImpl();
        User user = dao.getViewer(username, hashedPassword);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            if (remember != null) {
                Cookie usernameCookie = new Cookie("username", username);
                Cookie passwordCookie = new Cookie("password", password);
                usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                passwordCookie.setMaxAge(7 * 24 * 60 * 60);
                usernameCookie.setPath("/");
                passwordCookie.setPath("/");

                resp.addCookie(usernameCookie);
                resp.addCookie(passwordCookie);
            } else {
                // Clear cookies if unchecked
                Cookie usernameCookie = new Cookie("username", "");
                Cookie passwordCookie = new Cookie("password", "");
                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);

                resp.addCookie(usernameCookie);
                resp.addCookie(passwordCookie);
            }

            resp.sendRedirect(req.getContextPath() + "/main");
        } else {
            req.setAttribute("error", "Invalid username or password.");
            req.setAttribute("enteredUsername", username);
            req.getRequestDispatcher("userlogin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String enteredUsername = null;
        String enteredPassword = null;

        // Get cookies
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "username":
                        enteredUsername = cookie.getValue();
                        break;
                    case "password":
                        enteredPassword = cookie.getValue();
                        break;
                }
            }
        }

        // Set as request attributes for use in JSP
        req.setAttribute("enteredUsername", enteredUsername);
        req.setAttribute("enteredPassword", enteredPassword);

        // Forward instead of redirect, to preserve request attributes
        req.getRequestDispatcher("/userlogin.jsp").forward(req, resp);
    }

}
