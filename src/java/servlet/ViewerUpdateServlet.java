package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.user.Viewer;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/manager/userlist")
public class ViewerUpdateServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            System.out.println(">>> phoneNumber:"+ phone);

            Viewer viewer = userDAO.getViewer(userId); // Lấy viewer cũ để giữ created_at và password
            if (viewer != null) {
                viewer.setUsername(username);
                viewer.setFullName(fullName);
                viewer.setEmail(email);
                viewer.setPhoneNumber(phone);

                boolean success = userDAO.updateViewer(viewer);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/manager/userlist.jsp");

                } else {
                    response.getWriter().println("❌ Update failed.");
                }
            } else {
                response.getWriter().println("❌ Viewer not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error: " + e.getMessage());
        }
    }
}
