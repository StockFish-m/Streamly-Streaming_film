package dao;

import dao.DBConnection;
import model.content.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));
                map.put(genre.getGenreId(), genre);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // handle properly in production
        }

        return map;
    }

    @Override
    public List<Genre> getAllGenres() {
        if (genreMap == null || genreMap.isEmpty()) {
            genreMap = loadAllGenres();
        }
        return new ArrayList<>(genreMap.values());
    }

    @Override
    public List<Integer> getAllReleaseYears() {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(c.release_date) AS release_year "
                + "FROM [dbo].[Content] c "
                + "WHERE c.release_date IS NOT NULL AND c.is_active = 1 "
                + "ORDER BY release_year DESC";

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
        StringBuilder sql = new StringBuilder("SELECT c.* FROM [dbo].[Content] c WHERE c.is_active = 1");

        List<Object> params = new ArrayList<>();

        if (searchTerm != null && !searchTerm.isBlank()) {
            sql.append(" AND c.title LIKE ?");
            params.add("%" + searchTerm.trim() + "%");
        }

        if (genreId > 0) {
            sql.append(" AND EXISTS (SELECT 1 FROM [dbo].[ContentGenre] cg "
                    + "WHERE cg.content_id = c.content_id AND cg.genre_id = ?)");
            params.add(genreId);
        }

        if (releaseYear > 0) {
            sql.append(" AND c.release_date IS NOT NULL AND YEAR(c.release_date) = ?");
            params.add(releaseYear);
        }

        if (contentType != null && !contentType.isBlank()) {
            sql.append(" AND UPPER(c.type) = UPPER(?)");
            params.add(contentType.trim());
        }

        sql.append(" ORDER BY CASE WHEN c.release_date IS NULL THEN 1 ELSE 0 END, c.release_date DESC, c.title ASC");

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



    @Override
    public Genre getGenreById(int genreId) {
        if (genreMap == null || genreMap.isEmpty()) {
            genreMap = loadAllGenres();
        }
        return genreMap.get(genreId);
    }

    @Override
    public void addGenre(Genre genre) {
        String sql = "INSERT INTO [dbo].[Genre] (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, genre.getGenreName());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    genre.setGenreId(generatedKeys.getInt(1));
                }
            }

            if (genreMap == null) {
                genreMap = new LinkedHashMap<>();
            }
            genreMap.put(genre.getGenreId(), genre);
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
            boolean updated = ps.executeUpdate() > 0;
            if (updated) {
                if (genreMap == null) {
                    genreMap = new LinkedHashMap<>();
                }
                genreMap.put(genre.getGenreId(), genre);
            }
            return updated;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGenre(int genreId) {
        String sql = "DELETE FROM Genre WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, genreId);
            boolean deleted = ps.executeUpdate() > 0;
            if (deleted) {
                if (genreMap == null) {
                    genreMap = loadAllGenres();
                } else {
                    genreMap.remove(genreId);
                }
            }
            return deleted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
