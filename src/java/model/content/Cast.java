package model.content;

public class Cast {
    
    public enum CastRole {
        Actor,
        Director,
        Other
    }
    
    private String castId;
    private String castName;
    

    public Cast() {}

    public Cast(String id, String name) {
        this.castId = id;
        this.castName = name;
    }

    public String getId() {
        return castId;
    }

    public void setId(String id) {
        this.castId = id;
    }

    public String getName() {
        return castName;
    }

    public void setName(String name) {
        this.castName = name;
    }
}
