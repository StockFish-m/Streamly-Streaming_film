package servlet;

import dao.WatchHistoryDAO;
import dao.WatchHistoryDAOImpl;
import model.content.WatchHistory;
import model.user.Viewer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/watchhistory") // <- đây là URL không có .jsp
public class WatchHistoryServlet extends HttpServlet {

    private final WatchHistoryDAO watchHistoryDAO = new WatchHistoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Viewer user = (Viewer) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("userlogin.jsp");
            return;
        }

        List<WatchHistory> historyList = watchHistoryDAO.getDetailedWatchHistoriesByUserId(user.getUser_id());
        request.setAttribute("historyList", historyList);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("/watchhistory.jsp").forward(request, response);
    }
}
