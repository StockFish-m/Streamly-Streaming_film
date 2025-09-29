/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.content;

import java.util.Date;

/**
 *
 * @author MinhooMinh
 */
public class Movie extends Content {

    public Movie(int contentId, String title, String description, Date releaseDate, ContentType type, 
            String videoUrl, String thumbnailUrl, boolean isActive) {
        super(contentId, title, description, releaseDate, type, videoUrl, thumbnailUrl, isActive );
    }

    public Movie() {
    }
    
    
    
}
