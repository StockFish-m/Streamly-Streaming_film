/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.content.Content;
import model.content.Movie;

/**
 *
 * @author MinhooMinh
 */
public interface ContentDAO {
    
    //get content by its id
    Content getContent(int contentId);
    
    //get all contents
    List<Content> getAllContents();
    
    //add new content
    void addContent(Content content);
    
    //update an eisting content
    boolean updateContent(Content content);
    // delete content
    boolean deleteContent(int contentId);

    List<String> getGenresByContentId(int contentId);

    List<String> getCastByContentId(int contentId);
    
    List<Movie> getNewMovies(int amount);

    List<Content> searchContents(String genreId);
    
    boolean softDeleteContent(int contentId);
}
