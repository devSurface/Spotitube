package han.dea.spotitube.dylan.controllers.dto;

public class TrackDTO {

    private String title, performer, album, publicationDate, description;
    private int id, duration, playcount;
    private boolean offlineAvailable;

    public TrackDTO(int id,
                    String title,
                    String performer,
                    String album,
                    String publicationDate,
                    String description,
                    int duration,
                    int playcount,
                    boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.album = album;
        this.publicationDate = publicationDate;
        this.description = description;
        this.duration = duration;
        this.playcount = playcount;
        this.offlineAvailable = offlineAvailable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public boolean getOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }

}