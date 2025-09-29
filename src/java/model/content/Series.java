/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.content;

import java.util.Date;
import java.util.List;


public class Series extends Content {
    private List<Episode> episodes;

    public Series() {
    }

    public Series(int contentId, String title, String description, Date releaseDate, ContentType type, 
            String videoUrl, String thumbnailUrl, boolean isActive) {
        super(contentId, title, description, releaseDate, type, videoUrl, thumbnailUrl, isActive);
    }

    public Series(List<Episode> episodes, int contentId, String title, String description, Date releaseDate, ContentType type, 
            String videoUrl, String thumbnailUrl, boolean isActive) {
        super(contentId, title, description, releaseDate, type, videoUrl, thumbnailUrl, isActive);
        this.episodes = episodes;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    
     
    
}
