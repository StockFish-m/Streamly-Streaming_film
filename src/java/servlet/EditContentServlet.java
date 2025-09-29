package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import model.content.Content;
import model.content.Content.ContentType;
import model.content.Movie;
import model.content.Series;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/content_edit")
public class EditContentServlet extends HttpServlet {

    private ContentDAO contentDAO;

    @Override
    public void init() {
        contentDAO = new ContentDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contentIdParam = request.getParameter("id");

        if (contentIdParam != null && !contentIdParam.isEmpty()) {
            try {
                int contentId = Integer.parseInt(contentIdParam);
                Content content = contentDAO.getContent(contentId);
                request.setAttribute("content", content);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID không hợp lệ.");
            }
        }

        request.getRequestDispatcher("/content_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        request.setCharacterEncoding("UTF-8");
//
//        String idStr = request.getParameter("contentId");
//        String title = request.getParameter("title");
//        String description = request.getParameter("description");
//        String releaseDateStr = request.getParameter("releaseDate");
//        String videoUrl = request.getParameter("videoUrl");
//        String thumbnailUrl = request.getParameter("thumbnailUrl");
//        String typeStr = request.getParameter("type");
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date releaseDate = sdf.parse(releaseDateStr);
//
//            ContentType type = ContentType.valueOf(typeStr); // Movie or Series
//
//            int contentId = 0;
//            boolean isUpdate = false;
//
//            if (idStr != null && !idStr.isEmpty()) {
//                contentId = Integer.parseInt(idStr);
//                isUpdate = true;
//            }
//
//            Content content;
//            if (type == ContentType.Movie) {
//                content = new Movie(contentId, title, description, releaseDate, type, videoUrl, thumbnailUrl);
//            } else {
//                content = new Series(contentId, title, description, releaseDate, type, videoUrl, thumbnailUrl);
//            }
//
//            if (isUpdate) {
//                contentDAO.updateContent(content);
//            } else {
//                contentDAO.addContent(content);
//            }
//
//            response.sendRedirect("list");
//
//        } catch (ParseException e) {
//            request.setAttribute("error", "Ngày phát hành không đúng định dạng (yyyy-MM-dd).");
//            request.getRequestDispatcher("/content_edit.jsp").forward(request, response);
//
//        } catch (NumberFormatException e) {
//            request.setAttribute("error", "ID không hợp lệ.");
//            request.getRequestDispatcher("/content_edit.jsp").forward(request, response);
//
//        } catch (IllegalArgumentException e) {
//            request.setAttribute("error", "Loại nội dung không hợp lệ.");
//            request.getRequestDispatcher("/content_edit.jsp").forward(request, response);
//        }
    }
}
