package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.content.Content;
import model.content.WatchHistory;
import service.ContentService;

public class LibraryDAOImpl implements LibraryDAO {

    private static final String INSERT_SQL
            = "INSERT INTO WatchHistory (user_id, content_id, episode_id, watched_at) VALUES (?, ?, ?, GETDATE())";

    @Override
    public void insertWatchHistory(int userId, int contentId, Integer episodeId) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, contentId);
            if (episodeId != null) {
                ps.setInt(3, episodeId);
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // or use logger
        }
    }

    @Override
    public List<WatchHistory> getWatchHistoryByUserId(int userId) {
        List<WatchHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM WatchHistory WHERE user_id = ? ORDER BY watched_at DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WatchHistory history = new WatchHistory();
                history.setHistory_id(rs.getInt("history_id"));
                history.setUser_id(rs.getInt("user_id"));
                history.setContent_id(rs.getInt("content_id"));
                history.setEpisode_id(rs.getInt("episode_id"));
                history.setWatched_at(rs.getTimestamp("watched_at"));

                // optionally load content object
                //Content content = contentDAO.getContent(rs.getInt("content_id"));
                //history.setContent(content);
                historyList.add(history);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return historyList;
    }

    @Override
    public void removeFromWatchlist(int userId, int contentId) {
        String sql = "DELETE FROM WatchList WHERE user_id = ? AND content_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, contentId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertToWatchlist(int userId, int contentId) {
        String countSql = "SELECT COUNT(*) FROM WatchList WHERE user_id = ?";
        String existsSql = "SELECT 1 FROM WatchList WHERE user_id = ? AND content_id = ?";
        String insertSql = "INSERT INTO WatchList (user_id, content_id, priority, added_at) VALUES (?, ?, 1, GETDATE())";

        try (Connection conn = DBConnection.getConnection()) {

            // Step 1: Check if the content is already in the list
            try (PreparedStatement existsStmt = conn.prepareStatement(existsSql)) {
                existsStmt.setInt(1, userId);
                existsStmt.setInt(2, contentId);
                ResultSet rs = existsStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Content already in watchlist.");
                    return;
                }
            }

            // Step 2: Count items in watchlist
            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                countStmt.setInt(1, userId);
                ResultSet rs = countStmt.executeQuery();
                if (rs.next() && rs.getInt(1) >= 4) {
                    System.out.println("Watchlist limit reached. Cannot insert.");
                    return;
                }
            }

            // Step 3: Insert with priority 1
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, contentId);
                insertStmt.executeUpdate();
                System.out.println("Content added to watchlist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Content> getWatchListByUserId(int userId) {
        List<Content> watchList = new ArrayList<>();
        String sql = "SELECT c.* FROM WatchList w "
                + "JOIN Content c ON w.content_id = c.content_id "
                + "WHERE w.user_id = ? ORDER BY w.priority ASC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Content content = ContentService.extractContentFromResultSet(rs);

                    watchList.add(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // or log
        }
        return watchList;
    }

    @Override
    public int getWatchlistCount(int userId) {
        String sql = "SELECT COUNT(*) FROM WatchList WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//
//    @Override
//    public WatchHistory getWatchHistoryById(int historyId) {
//        String sql = "SELECT * FROM WatchHistory WHERE history_id = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, historyId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return mapResultSet(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void addWatchHistory(WatchHistory history) {
//        String sql = "INSERT INTO WatchHistory (content_id, user_id, episode_id, watched_at) VALUES (?, ?, ?, ?)";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, Integer.parseInt(history.getContent_id()));
//            ps.setInt(2, Integer.parseInt(history.getUser_id()));
//            if (history.getEpisode_id() != null && !history.getEpisode_id().isEmpty()) {
//                ps.setInt(3, Integer.parseInt(history.getEpisode_id()));
//            } else {
//                ps.setNull(3, Types.INTEGER);
//            }
//            ps.setTimestamp(4, new Timestamp(history.getWatched_at().getTime()));
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public boolean updateWatchHistory(WatchHistory history) {
//        String sql = "UPDATE WatchHistory SET content_id = ?, user_id = ?, episode_id = ?, watched_at = ? WHERE history_id = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, Integer.parseInt(history.getContent_id()));
//            ps.setInt(2, Integer.parseInt(history.getUser_id()));
//            if (history.getEpisode_id() != null && !history.getEpisode_id().isEmpty()) {
//                ps.setInt(3, Integer.parseInt(history.getEpisode_id()));
//            } else {
//                ps.setNull(3, Types.INTEGER);
//            }
//            ps.setTimestamp(4, new Timestamp(history.getWatched_at().getTime()));
//            ps.setInt(5, Integer.parseInt(history.getHistory_id()));
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteWatchHistory(int historyId) {
//        String sql = "DELETE FROM WatchHistory WHERE history_id = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, historyId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean hasWatched(int userId, int contentId, Integer episodeId) {
//        String sql = "SELECT 1 FROM WatchHistory WHERE user_id = ? AND content_id = ? AND (episode_id = ? OR (? IS NULL AND episode_id IS NULL))";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, userId);
//            ps.setInt(2, contentId);
//            if (episodeId != null) {
//                ps.setInt(3, episodeId);
//                ps.setInt(4, episodeId);
//            } else {
//                ps.setNull(3, Types.INTEGER);
//                ps.setNull(4, Types.INTEGER);
//            }
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private WatchHistory mapResultSet(ResultSet rs) throws SQLException {
//        return new WatchHistory(
//            String.valueOf(rs.getInt("history_id")),
//            String.valueOf(rs.getInt("content_id")),
//            String.valueOf(rs.getInt("user_id")),
//            rs.getObject("episode_id") != null ? String.valueOf(rs.getInt("episode_id")) : null,
//            rs.getTimestamp("watched_at")
//        );
//    }
}
