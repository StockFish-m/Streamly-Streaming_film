package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.Viewer;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Viewer user = (Viewer) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("userlogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        switch (action != null ? action : "") {
            case "edit":
                request.getRequestDispatcher("profile_edit.jsp").forward(request, response);
                break;
            case "watchhistory":
                request.getRequestDispatcher("watchhistory.jsp").forward(request, response);
                break;
            case "forgetpassword":
                request.getRequestDispatcher("forgetpassword.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Viewer user = (Viewer) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("userlogin.jsp");
            return;
        }

        int userId = user.getUser_id(); // Lấy từ session object
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            Viewer viewer = new Viewer();
            viewer.setUser_id(userId);
            viewer.setFullName(request.getParameter("fullName"));
            viewer.setEmail(request.getParameter("email"));
            viewer.setPhoneNumber(request.getParameter("phoneNumber"));

            boolean updated = userDAO.updateViewerProfile(viewer);
            request.setAttribute("message", updated ? "Update successful!" : "Update failed!");

            // Cập nhật lại thông tin trong session
            Viewer updatedViewer = userDAO.getViewer(userId);
            session.setAttribute("user", updatedViewer);
        }

        // Hiển thị lại profile sau update
        Viewer updatedUser = userDAO.getViewer(userId);
        request.setAttribute("user", updatedUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
