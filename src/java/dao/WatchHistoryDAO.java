package dao;

import java.util.List;
import model.content.WatchHistory;

public interface WatchHistoryDAO {

    // Lấy danh sách lịch sử xem theo user_id
    List<WatchHistory> getWatchHistoriesByUserId(int userId);

    // Lấy một bản ghi cụ thể theo history_id
    WatchHistory getWatchHistoryById(int historyId);

    // Thêm mới một bản ghi
    void addWatchHistory(WatchHistory history);

    // Cập nhật lịch sử xem
    boolean updateWatchHistory(WatchHistory history);

    // Xóa lịch sử xem
    boolean deleteWatchHistory(int historyId);

    // Kiểm tra xem user đã xem content hoặc episode chưa
    boolean hasWatched(int userId, int contentId, Integer episodeId);
    
    public List<WatchHistory> getDetailedWatchHistoriesByUserId(int userId);

}
