/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.Date;
import model.content.Content;
import model.content.Movie;
import model.content.Series;
import java.sql.*;

public class ContentService {
    public static Content extractContentFromResultSet(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("content_id");
        String title = rs.getString("title");
        String desc = rs.getString("description");
        Date releaseDate = rs.getDate("release_date");
        String typeStr = rs.getString("type").toUpperCase();
        Content.ContentType type = switch (typeStr) {
            case "MOVIE" ->
                Content.ContentType.Movie;
            case "SERIES" ->
                Content.ContentType.Series;
            default ->
                Content.ContentType.Other;
        };
        
        String videoUrl = rs.getString("video_url");
        String thumbnailUrl = rs.getString("thumbnail_url");
        boolean isActive = rs.getBoolean("is_active");
        
        if (type == Content.ContentType.Movie)
        {
            return new Movie(id, title, desc, releaseDate, type, videoUrl, thumbnailUrl, isActive);
        }
        else 
        {
            return new Series(id, title, desc, releaseDate, type, videoUrl, thumbnailUrl, isActive);
        }
    }
}
