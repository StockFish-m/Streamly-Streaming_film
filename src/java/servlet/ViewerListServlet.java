package servlet;

import dao.UserDAO;
import model.user.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.user.Viewer;

public class ViewerListServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new dao.UserDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Viewer> listUser = userDAO.getAllViewers();
        request.setAttribute("listUser", listUser);
        request.getRequestDispatcher("/function/list.jsp").forward(request, response);
    }
}
