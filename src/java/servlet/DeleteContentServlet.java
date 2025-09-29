package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteContentServlet extends HttpServlet {

    private final ContentDAO contentDAO = new ContentDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int contentId = Integer.parseInt(request.getParameter("id"));
            contentDAO.deleteContent(contentId);
            response.sendRedirect("list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid content ID.");
        }
    }
}
