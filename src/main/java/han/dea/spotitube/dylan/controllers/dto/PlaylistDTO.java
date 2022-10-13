package han.dea.spotitube.dylan.controllers.dto;

import jakarta.json.bind.annotation.JsonbVisibility;

import java.util.ArrayList;

public class PlaylistDTO {

    private int id;
    private boolean owner;
    private Integer owner_id;
    private String name;
    private ArrayList<TrackDTO> tracks;

    public PlaylistDTO() {}
    public PlaylistDTO(int id, boolean owner, String name, ArrayList<TrackDTO> tracks) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.tracks = tracks;
    }
    public PlaylistDTO(int id, String name, int owner_id, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner_id = owner_id;
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

    public boolean isOwner() {
        if (owner_id == null || owner_id == 0 ) {
            this.owner = false;
        } else {
            this.owner = true;
        }
        return owner;
    }

    public boolean getOwner() {
        return this.owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

}