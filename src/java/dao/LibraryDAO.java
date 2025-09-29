/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.content.Content;
import model.content.WatchHistory;
/**
 *
 * @author MinhooMinh
 */
public interface LibraryDAO {
    
    void insertWatchHistory(int userId, int contentId, Integer episodeId);
    
    // Lấy toàn bộ lịch sử xem của một người dùng theo user_id
    List<WatchHistory> getWatchHistoryByUserId(int userId);
    
    void insertToWatchlist(int userId, int contentId);
    
    void removeFromWatchlist(int userId, int contentId);
    
    List<Content> getWatchListByUserId(int userId);
    
    int getWatchlistCount(int userId);

    // Lấy lịch sử xem theo ID (nếu cần)
    //WatchHistory getWatchHistoryById(int historyId);

    // Thêm một lượt xem mới (content, episode, user, auto date)
    //void addWatchHistory(WatchHistory history);

    // Cập nhật lượt xem (ví dụ: cập nhật episode_id hoặc watched_at)
    //boolean updateWatchHistory(WatchHistory history);

    // Xóa lịch sử xem theo ID
    //boolean deleteWatchHistory(int historyId);

    // Kiểm tra xem người dùng đã xem content/episode đó chưa (nếu cần)
    //boolean hasWatched(int userId, int contentId, Integer episodeId);
}
