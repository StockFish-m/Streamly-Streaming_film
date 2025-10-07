/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.content.Content;
import model.content.WatchHistory;

public interface LibraryDAO {
    
    void insertWatchHistory(int userId, int contentId, Integer episodeId);
    
    
    List<WatchHistory> getWatchHistoryByUserId(int userId);
    
    void insertToWatchlist(int userId, int contentId);
    
    void removeFromWatchlist(int userId, int contentId);
    
    List<Content> getWatchListByUserId(int userId);
    
    int getWatchlistCount(int userId);

    
}
