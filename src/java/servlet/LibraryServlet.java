/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import dao.LibraryDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.content.Content;
import model.content.WatchHistory;
import model.user.User;
import dao.LibraryDAO;


@WebServlet(name = "LibraryServlet", urlPatterns = {"/library"})
public class LibraryServlet extends HttpServlet {
    private final LibraryDAO libraryDAO = new LibraryDAOImpl();

        
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
            out.println("<title>Servlet LibraryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LibraryServlet at " + request.getContextPath() + "</h1>");
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
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        List<WatchHistory> historyList = libraryDAO.getWatchHistoryByUserId(user.getUser_id());

        ContentDAO contentDAO = new ContentDAOImpl();
        Set<Integer> seenIds = new HashSet<>();
        List<Content> contentList = new ArrayList<>();

        for (WatchHistory history : historyList) {
            int contentId = history.getContent_id();
            if (seenIds.add(contentId)) { // only adds if not already present
                Content content = contentDAO.getContent(contentId);
                if (content != null) {
                    contentList.add(content);
                }
            }
        }
        
        List<Content> watchlistContent = libraryDAO.getWatchListByUserId(user.getUser_id());
        

        request.setAttribute("recently_movies", contentList);
        request.setAttribute("watch_movies", watchlistContent);
        request.getRequestDispatcher("library.jsp").forward(request, response);
    }

    
    
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
        // Get the logged-in user from session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("userlogin.jsp");
            return;
        }

        // Get contentId from request
        String contentIdParam = request.getParameter("contentId");
        if (contentIdParam == null || contentIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu contentId");
            return;
        }

        try {
            int contentId = Integer.parseInt(contentIdParam);

            // Perform deletion
            libraryDAO.removeFromWatchlist(user.getUser_id(), contentId);

            // Redirect back to the watchlist page or refresh current page
            response.sendRedirect("library"); // adjust path if needed

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
