/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.content.Content;
import model.content.Content.ContentType;
import java.sql.SQLException;
import model.content.Movie;
import java.util.Date;
import model.content.Movie;
import model.content.Series;
import service.ContentService;


public class ContentDAOImpl implements ContentDAO {

    private Connection conn;

    public ContentDAOImpl() {
        // constructor mặc định không cần gì cả
        
    }

    public ContentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Content getContent(int contentId) {
        Content content = null;
        String sql = "SELECT * FROM Content WHERE content_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                content = ContentService.extractContentFromResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public List<Content> getAllContents() {
        List<Content> contents = new ArrayList<>();
        String sql = "SELECT * FROM Content";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                contents.add(ContentService.extractContentFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Override
    public void addContent(Content content) {
        String sql = "INSERT INTO Content (title, description, release_date, type, video_url, thumbnail_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, content.getTitle());
            stmt.setString(2, content.getDescription());
            stmt.setDate(3, new java.sql.Date(content.getReleaseDate().getTime()));
            stmt.setString(4, content.getType().name().toLowerCase()); // store as 'movie' or 'series'
            stmt.setString(5, content.getVideoUrl());
            stmt.setString(6, content.getThumbnailUrl());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateContent(Content content) {
        String sql = "UPDATE Content SET title = ?, description = ?, release_date = ?, type = ?, video_url = ?, thumbnail_url = ? WHERE content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, content.getTitle());
            stmt.setString(2, content.getDescription());
            stmt.setDate(3, new java.sql.Date(content.getReleaseDate().getTime()));
            stmt.setString(4, content.getType().name().toLowerCase());
            stmt.setString(5, content.getVideoUrl());
            stmt.setString(6, content.getThumbnailUrl());
            stmt.setInt(7, content.getContentId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    private Content extractContentFromResultSet(ResultSet rs) throws SQLException {
//        //public Movie(int contentId, String title, String description, Date releaseDate, ContentType type, String videoUrl, String thumbnailUrl) {
//        int id = rs.getInt("content_id");
//        String title = rs.getString("title");
//        String desc = rs.getString("description");
//        Date releaseDate = rs.getDate("release_date");
//        String typeStr = rs.getString("type").toUpperCase();
//        ContentType type = switch (typeStr) {
//            case "MOVIE" ->
//                ContentType.Movie;
//            case "SERIES" ->
//                ContentType.Series;
//            default ->
//                ContentType.Other;
//        };
//        
//        String videoUrl = rs.getString("video_url");
//        String thumbnailUrl = rs.getString("thumbnail_url");
//        boolean isActive = rs.getBoolean("is_active");
//        
//        if (type == ContentType.Movie)
//        {
//            return new Movie(id, title, desc, releaseDate, type, videoUrl, thumbnailUrl, isActive);
//        }
//        else 
//        {
//            return new Series(id, title, desc, releaseDate, type, videoUrl, thumbnailUrl, isActive);
//        }
//    }

    @Override
    public boolean deleteContent(int contentId) {

        String sql = "UPDATE is_active = False FROM Content WHERE content_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getGenresByContentId(int contentId) {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT g.genre_name FROM Genre g "
                + "JOIN ContentGenre cg ON g.genre_id = cg.genre_id "
                + "WHERE cg.content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("genre_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genres;
    }

    @Override
    public List<String> getCastByContentId(int contentId) {
        List<String> casts = new ArrayList<>();
        String sql = "SELECT c.name FROM Cast c "
                + "JOIN ContentCast cc ON c.cast_id = cc.cast_id "
                + "WHERE cc.content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                casts.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return casts;
    }

    @Override
    public List<Movie> getNewMovies(int amount) {
        List<Movie> list = new ArrayList<>();

        // SQL Server uses TOP, but it must be a literal value (not ?)
        String sql = "SELECT TOP " + amount + " * FROM Content ORDER BY release_date DESC";

        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie content = new Movie();
                content.setContentId(rs.getInt("content_id"));
                content.setTitle(rs.getString("title"));
                content.setDescription(rs.getString("description"));
                content.setVideoUrl(rs.getString("video_url"));
                content.setThumbnailUrl(rs.getString("thumbnail_url"));
                content.setReleaseDate(rs.getTimestamp("release_date")); // or rs.getDate() if it's DATE
                list.add(content);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging this
        }

        return list;
    }

    
    @Override
    public List<Content> searchContents(String genreId) {
        List<Content> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM Content c "
                + "JOIN ContentGenre cg ON c.content_id = cg.content_id "
                + "WHERE (? IS NULL OR cg.genre_id = ?) AND c.is_active = 1";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (genreId == null || genreId.isEmpty()) {
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, Integer.parseInt(genreId));
                ps.setInt(2, Integer.parseInt(genreId));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                Content content;

                if ("movie".equalsIgnoreCase(type)) {
                    content = new Movie();
                } else if ("series".equalsIgnoreCase(type)) {
                    content = new Series();
                } else {
                    continue; // hoặc throw lỗi nếu type không hợp lệ
                }

                content.setContentId(rs.getInt("content_id"));
                content.setTitle(rs.getString("title"));
                content.setDescription(rs.getString("description"));
                content.setThumbnailUrl(rs.getString("thumbnail_url"));
                content.setVideoUrl(rs.getString("video_url"));

                list.add(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean softDeleteContent(int contentId) {
        String sql = "UPDATE Content SET is_active = 0 WHERE content_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, contentId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
