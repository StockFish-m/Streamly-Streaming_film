/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import dao.LibraryDAO;
import dao.LibraryDAOImpl;
import dao.UserReviewDAO;
import dao.UserReviewDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.content.Content;
import model.user.UserReview;
import model.user.User;
import model.user.Viewer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class DetailServlet extends HttpServlet {

    private ContentDAO contentDAO = new ContentDAOImpl();
    private UserReviewDAO reviewDAO = new UserReviewDAOImpl();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Viewer user = (session != null) ? (Viewer) session.getAttribute("user") : null;

        // Force login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/userlogin");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số id");
            return;
        }

        int contentId;
        try {
            contentId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tham số id không hợp lệ");
            return;
        }

        // Fetch content
        Content content = contentDAO.getContent(contentId);
        if (content == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy nội dung");
            return;
        }

        List<String> genres = contentDAO.getGenresByContentId(contentId);
        List<String> casts = contentDAO.getCastByContentId(contentId);

        double averageRating = reviewDAO.getAverageRatingByContentId(contentId);
        List<UserReview> latestReviews = reviewDAO.getReviewsByContent(contentId);

        // Fetch user's review if exists
        UserReview myReview = reviewDAO.getReviewByUserAndContent(user.getUser_id(), contentId);
        request.setAttribute("userReview", myReview);

        // If needed, refresh user's subscription
        //ViewerSubscription sub = subscriptionDAO.getActiveSubscription(user.getId());
        //user.setActiveSubscription(sub);
        // Set attributes
        request.setAttribute("user", user);
        request.setAttribute("content", content);
        request.setAttribute("genres", genres);
        request.setAttribute("casts", casts);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("latestReviews", latestReviews);

        // Forward to detail.jsp
        request.getRequestDispatcher("detail.jsp").forward(request, response);
    }

    private final LibraryDAO libraryDAO = new LibraryDAOImpl();

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("userlogin.jsp");
            return;
        }

        String contentIdParam = request.getParameter("contentId");

        if (contentIdParam == null || contentIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu contentId");
            return;
        }

        try {
            int contentId = Integer.parseInt(contentIdParam);
            String action = request.getParameter("action");

            if ("addToWatchlist".equals(action)) {
                int currentCount = libraryDAO.getWatchlistCount(user.getUser_id());
                if (currentCount >= 4) {
                    request.setAttribute("feedbackMessage", "❌ Danh sách của bạn đã đầy (tối đa 4 mục)");
                } else {
                    libraryDAO.insertToWatchlist(user.getUser_id(), contentId);
                    request.setAttribute("feedbackMessage", "✅ Đã thêm vào danh sách của tôi");
                }

            } else if ("submitReview".equals(action)) {
                String ratingParam = request.getParameter("rating");
                String comment = request.getParameter("comment");

                if (ratingParam != null && comment != null) {
                    try {
                        int rating = Integer.parseInt(ratingParam);

                        UserReview existingReview = reviewDAO.getReviewByUserAndContent(user.getUser_id(), contentId);
                        if (existingReview != null) {
                            existingReview.setRating(rating);
                            existingReview.setComment(comment);
                            LocalDate localDate = LocalDate.now();
                            Date date2 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                            existingReview.setReviewDate(date2);
                            reviewDAO.updateReview(existingReview);
                            request.setAttribute("feedbackMessage", "✏️ Đã cập nhật đánh giá của bạn");
                        } else {
                            UserReview newReview = new UserReview();
                            newReview.setUserId(user.getUser_id());
                            newReview.setContentId(contentId);
                            newReview.setRating(rating);
                            newReview.setComment(comment);

                            LocalDate localDate = LocalDate.now();
                            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                            newReview.setReviewDate(date);
                            reviewDAO.insertReview(newReview);
                            request.setAttribute("feedbackMessage", "✅ Đã thêm đánh giá của bạn");
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("feedbackMessage", "⚠️ Điểm đánh giá không hợp lệ");
                    }
                } else {
                    request.setAttribute("feedbackMessage", "⚠️ Thiếu thông tin đánh giá");
                }
            }

            // Re-fetch content and display
            Content content = contentDAO.getContent(contentId);
            List<String> genres = contentDAO.getGenresByContentId(contentId);
            List<String> casts = contentDAO.getCastByContentId(contentId);
            double averageRating = reviewDAO.getAverageRatingByContentId(contentId);
            List<UserReview> latestReviews = reviewDAO.getReviewsByContent(contentId);

            request.setAttribute("content", content);
            request.setAttribute("genres", genres);
            request.setAttribute("casts", casts);
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("latestReviews", latestReviews);

            request.getRequestDispatcher("detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "contentId không hợp lệ");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
