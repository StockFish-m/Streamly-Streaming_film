package servlet;

import dao.ViewerSubscriptionDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.subscription.ViewerSubscription;
import model.user.Viewer;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/subscribe")
public class SubscriptionServlet extends HttpServlet {

    private ViewerSubscriptionDAOImpl subscriptionDAO;

    @Override
    public void init() {
        subscriptionDAO = new ViewerSubscriptionDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Viewer viewer = (Viewer) session.getAttribute("user");

        if (viewer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String planIdParam = request.getParameter("plan_id");
        if (planIdParam == null || planIdParam.isEmpty()) {
            response.sendRedirect("subscription.jsp?error=missing_plan");
            return;
        }

        try {
            int planId = Integer.parseInt(planIdParam);

            // Đăng ký gói mới
            subscriptionDAO.subscribe(viewer.getUser_id(), planId);

            // Lấy gói mới nhất
            ViewerSubscription latestSub = subscriptionDAO.getLatestByViewerId(viewer.getUser_id());
            viewer.setActiveSubscription(latestSub);
            session.setAttribute("user", viewer);

            // Gửi thông tin gói mới sang payment.jsp
            request.setAttribute("subscription", latestSub);
            request.getRequestDispatcher("payment.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("subscription.jsp?error=invalid_plan_id");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("subscription.jsp?error=sql_exception");
        }
    }
}
