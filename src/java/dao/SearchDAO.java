package dao;

import java.util.List;
import model.content.Content;
import model.content.Genre;

public interface SearchDAO {
    
    //client side
    List<Genre> getAllGenres();
    
    Genre getGenreById(int id);
    
    List<Integer> getAllReleaseYears();
    
    List<Content> searchContents(String searchTerm, int genreId, int releaseYear, String contentType);
    
    //employee side
    void addGenre(Genre genre);
    boolean updateGenre(Genre genre);
    boolean deleteGenre(int genreId);
}
