///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package servlet;
//
//import dao.UserReviewDAO;
//import dao.UserReviewDAOImpl;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.util.Date;
//import model.user.UserReview;
//import model.user.Viewer;
//
///**
// *
// * @author DELL
// */
//public class ContentReviewServlet extends HttpServlet {
//    private final UserReviewDAO reviewDAO = new UserReviewDAOImpl();
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SubmitReviewServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SubmitReviewServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            int contentId = Integer.parseInt(request.getParameter("contentId"));
//            int rating = Integer.parseInt(request.getParameter("rating"));
//            String comment = request.getParameter("comment");
//
//            HttpSession session = request.getSession();
//            Viewer user = (Viewer) session.getAttribute("user");
//            if (user == null) {
//                response.sendRedirect("login.jsp");
//                return;
//            }
//
//            int userId = user.getUser_id();
//
//            if (!reviewDAO.hasUserReviewed(userId, contentId)) {
//                UserReview review = new UserReview();
//                review.setUserId(userId);
//                review.setContentId(contentId);
//                review.setRating(rating);
//                review.setComment(comment);
//                review.setReviewDate(new Date());
//
//                reviewDAO.insertReview(review);
//            }
//
//            response.sendRedirect("watch?id=" + contentId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("error.jsp");
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
