/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.content.Content;


public class ContentActiveFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            idParam = req.getParameter("contentId"); // Try alternative param from POST
        }

        try {
            int contentId = Integer.parseInt(idParam);
            ContentDAO dao = new ContentDAOImpl();
            Content content = dao.getContent(contentId);

            if (content == null || !content.isIsActive()) {
                // Nếu không tồn tại hoặc không hoạt động
                req.getRequestDispatcher("error-content.jsp").forward(req, resp);
                return;
            }

            chain.doFilter(request, response); // Cho phép đi tiếp

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}