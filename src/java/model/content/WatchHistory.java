package model.content;

import java.util.Date;

public class WatchHistory {
    private int history_id;
    private int content_id;
    private int user_id;
    private int episode_id;
    private Date watched_at;

    // Optional display fields (used in some queries)
    private String title;
    private String video_url;
    
    public WatchHistory()
    {
        
    }

    public WatchHistory(int history_id, int content_id, int user_id, int episode_id, Date watched_at) {
        this.history_id = history_id;
        this.content_id = content_id;
        this.user_id = user_id;
        this.episode_id = episode_id;
        this.watched_at = watched_at;
    }

    // Extended constructor for join queries
    public WatchHistory(int history_id, int content_id, int user_id, int episode_id, Date watched_at, String title, String video_url) {
        this(history_id, content_id, user_id, episode_id, watched_at);
        this.title = title;
        this.video_url = video_url;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(int episode_id) {
        this.episode_id = episode_id;
    }

    public Date getWatched_at() {
        return watched_at;
    }

    public void setWatched_at(Date watched_at) {
        this.watched_at = watched_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
