package han.dea.spotitube.dylan.controllers.controller;

import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.datasource.dao.TrackDAO;
import jakarta.inject.Inject;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TrackController {
    private TrackDAO trackDAO;
    public JSONObject getAllTracksInPlaylist(int playlistId) {
        JSONObject tracks = new JSONObject();
        tracks.put("tracks", trackDAO.getAllTracksInPlaylist(playlistId));
        return tracks;
    }
    public JSONObject getAvailableTracks(int playlistId) {
        JSONObject tracks = new JSONObject();
        tracks.put("tracks", trackDAO.getAvailableTracks(playlistId));
        return tracks;
    }
    public JSONObject addTrackToPlaylist(int playlistId, TrackDTO track) {
        trackDAO.addTrackToPlaylist(playlistId, track);

        JSONObject tracks = new JSONObject();
        tracks.put("tracks", trackDAO.getAllTracksInPlaylist(playlistId));
        return tracks;
    }
    public JSONObject removeTrackFromPlaylist(int playlistId, int trackId) {
        trackDAO.removeTrackFromPlaylist(playlistId, trackId);

        JSONObject tracks = new JSONObject();
        tracks.put("tracks", trackDAO.getAllTracksInPlaylist(playlistId));
        return tracks;
    }

 

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
