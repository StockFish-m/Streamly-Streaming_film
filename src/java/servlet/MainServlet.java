package servlet;

import dao.ContentDAO;
import dao.ContentDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.content.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    private final ContentDAO contentDAO = new ContentDAOImpl();

    @Override
   
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Lấy nhiều phim để có dữ liệu random đủ
    List<Movie> originalList = contentDAO.getNewMovies(60);
    Collections.shuffle(originalList); // Shuffle toàn bộ danh sách

    // Chia ra các dòng phim (6 dòng x 5 phim)
    List<Movie> newMovies = getSublist(originalList, 0, 10);
List<Movie> hotMovies = getSublist(originalList, 10, 20);
List<Movie> trendingMovies = getSublist(originalList, 20, 30);
List<Movie> recommended = getSublist(originalList, 30, 40);
List<Movie> action = getSublist(originalList, 40, 50);
List<Movie> popular = getSublist(originalList, 50, 60);


    // Gán các danh sách vào request
    request.setAttribute("new_movies", newMovies);
    request.setAttribute("hot_movies", hotMovies);
    request.setAttribute("trending_movies", trendingMovies);
    request.setAttribute("recommended", recommended);
    request.setAttribute("action", action);
    request.setAttribute("popular", popular);

    request.getRequestDispatcher("/main.jsp").forward(request, response);
}


    private List<Movie> getSublist(List<Movie> list, int from, int to) {
        return list.subList(Math.min(from, list.size()), Math.min(to, list.size()));
    }
}
