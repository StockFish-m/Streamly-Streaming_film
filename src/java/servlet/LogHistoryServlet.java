/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.LibraryDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.stream.Collectors;
import org.json.JSONObject;
import dao.LibraryDAO;

@WebServlet("/log_history")
public class LogHistoryServlet extends HttpServlet {

    private final LibraryDAO historyDAO = new LibraryDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        BufferedReader reader = request.getReader();
        String body = reader.lines().collect(Collectors.joining());
        JSONObject json = new JSONObject(body);

        int userId = json.getInt("userId");
        int contentId = json.getInt("contentId");
        int episodeId = json.optInt("episodeId", 0); // optional

        historyDAO.insertWatchHistory(userId, contentId, episodeId == 0 ? null : episodeId);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
