package servlet;

import dao.UserDAO;
import model.user.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class UserEditServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new dao.UserDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        User user = userDAO.getViewer(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/function/editUser.jsp").forward(request, response);
    }
}