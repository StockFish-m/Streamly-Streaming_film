/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import dao.SearchDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.content.Content;
import model.content.Genre;
import dao.SearchDAO;


public class SearchServlet extends HttpServlet {

    private final SearchDAO searchDAO = new SearchDAOImpl();

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
            out.println("<title>Servlet SearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServlet at " + request.getContextPath() + "</h1>");
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
        handleSearch(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleSearch(request, response);
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchTermRaw = request.getParameter("searchTerm");
        String genreIdParam = request.getParameter("genreId");
        String yearParam = request.getParameter("releaseYearId");
        String selectedTypeParam = request.getParameter("typeId");

        String searchTerm = (searchTermRaw != null) ? searchTermRaw.trim() : null;
        if (searchTerm != null && searchTerm.isEmpty()) {
            searchTerm = null;
        }

        String selectedTypeId = (selectedTypeParam != null) ? selectedTypeParam.trim() : null;
        if (selectedTypeId != null && selectedTypeId.isEmpty()) {
            selectedTypeId = null;
        }

        int selectedGenreId = 0;
        int selectedReleaseYear = 0;

        try {
            selectedGenreId = genreIdParam != null ? Integer.parseInt(genreIdParam.trim()) : 0;
        } catch (NumberFormatException e) {
            selectedGenreId = 0;
        }

        try {
            selectedReleaseYear = yearParam != null ? Integer.parseInt(yearParam.trim()) : 0;
        } catch (NumberFormatException e) {
            selectedReleaseYear = 0;
        }

        int pageSize = 20;
        int currentPage = 1;
        try {
            currentPage = request.getParameter("page") != null
                    ? Integer.parseInt(request.getParameter("page"))
                    : 1;
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        List<Genre> genres = searchDAO.getAllGenres();
        List<Integer> releaseYears = searchDAO.getAllReleaseYears();

        List<Content> fullResults = searchDAO.searchContents(searchTerm, selectedGenreId, selectedReleaseYear, selectedTypeId);
        int totalResults = fullResults.size();
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        int start = Math.max(0, (currentPage - 1) * pageSize);
        int end = Math.min(start + pageSize, totalResults);

        List<Content> pageResults = fullResults.subList(start, end);

        // Attributes for filters
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("genreId", selectedGenreId);
        request.setAttribute("releaseYearId", selectedReleaseYear);
        request.setAttribute("typeId", selectedTypeId);

        // Attributes for rendering
        request.setAttribute("genreList", genres);
        request.setAttribute("releaseYearList", releaseYears);
        request.setAttribute("searchResults", pageResults);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

}
