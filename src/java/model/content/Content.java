/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.content;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public abstract class Content {
    public enum ContentType {
        Movie,
        Series,
        Other
    }

    
    private int contentId;
    private String title;
    private String description;
    private Date releaseDate;
    private ContentType type;
    private String videoUrl;
    private String thumbnailUrl;
    private ArrayList<Genre> genres;
    private ArrayList<Cast> casts;
    private boolean isActive;

    protected Content()
    {
        genres = new ArrayList<>();
        casts = new ArrayList<>();
    }
    
    protected Content(int contentId, String title, String description, Date releaseDate, ContentType type, 
            String videoUrl, String thumbnailUrl, boolean isActive) {
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.type = type;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        genres = new ArrayList<>();
        casts = new ArrayList<>();
        this.isActive = isActive;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }
    
    public void addGenre(Genre genre)
    {
        genres.add(genre);
    }
    
    public void addCast(Cast cast)
    {
        casts.add(cast);
    }
    

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    
    public int getReleaseYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseDate);
        return cal.get(Calendar.YEAR);
    }
    
}
