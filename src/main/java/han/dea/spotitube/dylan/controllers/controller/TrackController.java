package han.dea.spotitube.dylan.controllers.controller;

import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.datasource.dao.TrackDAO;
import jakarta.inject.Inject;

import java.util.ArrayList;

public class TrackController {
    private TrackDAO trackDAO;
    public ArrayList<TrackDTO> getAllTracksInPlaylist(int playlistId) {
        return trackDAO.getAllTracksInPlaylist(playlistId);
    }
    public void getAvailableTracks() {}
    public void addTrackToPlaylist() {}
    public void removeTrackFromPlaylist() {}
 

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

}
