/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.content.Content;
import model.content.Movie;
import model.content.Series;


public class ContentManagerServlet extends HttpServlet {
    
    private ContentDAO contentDAO;

    @Override
    public void init() throws ServletException {
        contentDAO = new ContentDAOImpl();
    }

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
            out.println("<title>Servlet ContentManagerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ContentManagerServlet at " + request.getContextPath() + "</h1>");
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
        System.out.println("hellooooo");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteContent(request, response);
                break;
            default:
                listContent(request, response);
        }
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("insert".equals(action)) {
            insertContent(request, response);
        } else if ("update".equals(action)) {
            updateContent(request, response);
        }
    }

    private void listContent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Content> list = contentDAO.getAllContents();
        request.setAttribute("contents", list);
        request.getRequestDispatcher("/manager/contentList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("action", "insert");
        request.getRequestDispatcher("/manager/contentForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Content content = contentDAO.getContent(id);
        request.setAttribute("content", content);
        request.setAttribute("action", "update");
        request.getRequestDispatcher("/manager/contentForm.jsp").forward(request, response);
    }

    private void insertContent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Content content = getContentFromRequest(request);
        contentDAO.addContent(content);
        response.sendRedirect("contentManager");
    }

    private void updateContent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Content content = getContentFromRequest(request);
        content.setContentId(Integer.parseInt(request.getParameter("id")));
        contentDAO.updateContent(content);
        response.sendRedirect("contentManager");
    }

    private void deleteContent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        contentDAO.softDeleteContent(id); // soft delete
        response.sendRedirect("contentManager");
    }

    private Content getContentFromRequest(HttpServletRequest request) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String releaseDateStr = request.getParameter("releaseDate");
        String typeStr = request.getParameter("type");
        String videoUrl = request.getParameter("videoUrl");
        String thumbnailUrl = request.getParameter("thumbnailUrl");

        Date releaseDate = new Date();
        try {
            releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Content.ContentType type = Content.ContentType.valueOf(typeStr);

        if (type == Content.ContentType.Movie) {
            return new Movie(0, title, description, releaseDate, type, videoUrl, thumbnailUrl, true);
        } else {
            return new Series(0, title, description, releaseDate, type, videoUrl, thumbnailUrl, true);
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
