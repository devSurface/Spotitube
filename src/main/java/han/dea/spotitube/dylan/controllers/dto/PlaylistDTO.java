package han.dea.spotitube.dylan.controllers.dto;

import java.util.ArrayList;

public class PlaylistDTO {

    private int id;
    private Integer owner;
    private String name;
    private ArrayList<TrackDTO> tracks;

    public PlaylistDTO(int id, String name, int owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner(int id) {
        return (owner == id);
    }

    public int getOwner() {
        return this.owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }
}