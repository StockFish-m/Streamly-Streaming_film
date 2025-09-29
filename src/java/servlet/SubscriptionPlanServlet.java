package servlet;

import dao.SubscriptionPlanDAO;
import dao.UserDAO;
import dao.UserDAOImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import model.subscription.SubscriptionPlan;
import model.user.User;

@WebServlet("/subscription")
public class SubscriptionPlanServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // false = don't create if doesn't exist
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");

            // Step 2: Reload user from DB if exists
            if (user != null) {
                String username = user.getUsername();
                int id = user.getUser_id(); // assume it's already hashed

                UserDAO userDao = new UserDAOImpl();
                User updatedUser = userDao.getViewer(id);

                if (updatedUser != null) {
                    session.setAttribute("user", updatedUser); // update session with fresh user data
                    user = updatedUser;
                }
            }
        }

        // Step 3: Load subscription plans as usual
        SubscriptionPlanDAO dao = new SubscriptionPlanDAO();
        List<SubscriptionPlan> plans = dao.getAllPlans();
        request.setAttribute("plans", plans);

        request.getRequestDispatcher("/subscriptionPlan.jsp").forward(request, response);
    }
}
