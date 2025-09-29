package dao;

import dao.DBConnection;
import model.content.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import model.content.Content;
import service.ContentService;

public class SearchDAOImpl implements SearchDAO {

    private static Map<Integer, Genre> genreMap;

    public SearchDAOImpl() {
        if (genreMap == null) {
            genreMap = loadAllGenres();
        }
    }

    private Map<Integer, Genre> loadAllGenres() {
        Map<Integer, Genre> map = new HashMap<>();
        String sql = "SELECT * FROM Genre";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
                map.put(genre.getGenreId(), genre);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // handle properly in production
        }

        return map;
    }

    @Override
    public List<Genre> getAllGenres() {
        return new ArrayList<>(genreMap.values());
    }

    @Override
    public List<Integer> getAllReleaseYears() {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(release_date) AS release_year FROM Content ORDER BY release_year DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                years.add(rs.getInt("release_year"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle properly in production
        }

        return years;
    }

    @Override
    public List<Content> searchContents(String searchTerm, int genreId, int releaseYear, String contentType) {
        List<Content> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Content WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + searchTerm + "%");
        }

        if (genreId > 0) {
            sql.append(" AND content_id IN (SELECT content_id FROM ContentGenre WHERE genre_id = ?)");
            params.add(genreId);
        }

        if (releaseYear > 0) {
            sql.append(" AND YEAR(release_date) = ?");
            params.add(releaseYear);
        }

        if (contentType != null && !contentType.isEmpty()) {
            sql.append(" AND type = ?");
            params.add(contentType);
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Content content = ContentService.extractContentFromResultSet(rs);
                    result.add(content);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


//    @Override
//    public Genre getGenreById(String id) {
//        String sql = "SELECT * FROM Genre WHERE id = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new Genre(rs.getString("id"), rs.getString("name"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    @Override
    public Genre getGenreById(int genreId) {
        Genre genre = genreMap.get(genreId);
        if (genre == null) {
            System.out.println("Genre not found for ID: " + genreId);
        }
        return genre;
    }

    @Override
    public void addGenre(Genre genre) {
        String sql = "INSERT INTO Genre (id, name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, genre.getGenreId());
            ps.setString(2, genre.getGenreName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateGenre(Genre genre) {
        String sql = "UPDATE Genre SET name = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, genre.getGenreName());
            ps.setInt(2, genre.getGenreId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGenre(String id) {
        String sql = "DELETE FROM Genre WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
