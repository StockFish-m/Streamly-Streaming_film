package dao;

import model.content.WatchHistory;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WatchHistoryDAOImpl implements WatchHistoryDAO {

    @Override
    public List<WatchHistory> getWatchHistoriesByUserId(int userId) {
        List<WatchHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[WatchHistory] WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public WatchHistory getWatchHistoryById(int historyId) {
        String sql = "SELECT * FROM [dbo].[WatchHistory] WHERE history_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, historyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addWatchHistory(WatchHistory history) {
        String sql = "INSERT INTO [dbo].[WatchHistory] (content_id, user_id, episode_id, watched_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, history.getContent_id());
            ps.setInt(2, history.getUser_id());

            if (history.getEpisode_id() > 0) {
                ps.setInt(3, history.getEpisode_id());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (history.getWatched_at() != null) {
                ps.setTimestamp(4, new Timestamp(history.getWatched_at().getTime()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateWatchHistory(WatchHistory history) {
        String sql = "UPDATE [dbo].[WatchHistory] SET content_id = ?, user_id = ?, episode_id = ?, watched_at = ? WHERE history_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, history.getContent_id());
            ps.setInt(2, history.getUser_id());

            if (history.getEpisode_id() > 0) {
                ps.setInt(3, history.getEpisode_id());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (history.getWatched_at() != null) {
                ps.setTimestamp(4, new Timestamp(history.getWatched_at().getTime()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            ps.setInt(5, history.getHistory_id());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteWatchHistory(int historyId) {
        String sql = "DELETE FROM [dbo].[WatchHistory] WHERE history_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, historyId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasWatched(int userId, int contentId, Integer episodeId) {
        String sql = "SELECT 1 FROM [dbo].[WatchHistory] WHERE user_id = ? AND content_id = ? AND (episode_id = ? OR (? IS NULL AND episode_id IS NULL))";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, contentId);

            if (episodeId != null) {
                ps.setInt(3, episodeId);
                ps.setInt(4, episodeId);
            } else {
                ps.setNull(3, Types.INTEGER);
                ps.setNull(4, Types.INTEGER);
            }

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private WatchHistory mapResultSet(ResultSet rs) throws SQLException {
        int historyId = rs.getInt("history_id");
        int contentId = rs.getInt("content_id");
        int userId = rs.getInt("user_id");
        int episodeId = rs.getObject("episode_id") != null ? rs.getInt("episode_id") : 0;
        Date watchedAt = rs.getTimestamp("watched_at");

        return new WatchHistory(historyId, contentId, userId, episodeId, watchedAt);
    }
    
    @Override
public List<WatchHistory> getDetailedWatchHistoriesByUserId(int userId) {
    List<WatchHistory> list = new ArrayList<>();
    String sql = """
        WITH latest AS (
            SELECT content_id, MAX(watched_at) AS max_watched
            FROM [dbo].[WatchHistory]
            WHERE user_id = ?
            GROUP BY content_id
        )
        SELECT wh.history_id, wh.content_id, wh.user_id, wh.episode_id, wh.watched_at,
               ISNULL(e.title, c.title) AS title,
               ISNULL(e.video_url, c.video_url) AS video_url,
               c.thumbnail_url AS thumbnail_url
        FROM [dbo].[WatchHistory] wh
        JOIN latest l ON l.content_id = wh.content_id AND l.max_watched = wh.watched_at
        LEFT JOIN [dbo].[Episodes] e ON wh.episode_id = e.episode_id
        JOIN [dbo].[Content] c ON wh.content_id = c.content_id
        WHERE wh.user_id = ?
        ORDER BY wh.watched_at DESC
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int historyId = rs.getInt("history_id");
            int contentId = rs.getInt("content_id");
            int uId = rs.getInt("user_id");
            int episodeId = rs.getObject("episode_id") != null ? rs.getInt("episode_id") : 0;
            Timestamp watchedAt = rs.getTimestamp("watched_at");
            String title = rs.getString("title");
            String videoUrl = rs.getString("video_url");

            String thumbnailUrl = rs.getString("thumbnail_url");

            WatchHistory wh = new WatchHistory(historyId, contentId, uId, episodeId, watchedAt, title, videoUrl, thumbnailUrl);
            list.add(wh);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
