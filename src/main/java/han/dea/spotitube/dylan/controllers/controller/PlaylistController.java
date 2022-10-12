package han.dea.spotitube.dylan.controllers.controller;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.datasource.dao.PlaylistDAO;
import han.dea.spotitube.dylan.datasource.dao.TrackDAO;
import jakarta.inject.Inject;
import jakarta.json.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaylistController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;
    public JSONObject getAllPlaylists() {
        ArrayList<PlaylistDTO> playlists = playlistDAO.getAll();
        int length = 0;
        for (PlaylistDTO playlist : playlists) {
            ArrayList<TrackDTO> tracks = trackDAO.getAllTracksInPlaylist(playlist.getId());
            playlist.setTracks(tracks);

            for(TrackDTO track : playlist.getTracks()) {
                length += track.getDuration();
            }
        }

        JSONObject response = new JSONObject();
        response.put("playlists", playlists);
        response.put("length", length);

        return response;
    }
    public void deletePlaylist() {}
    public void addPlaylist() {}
    public void editPlaylist() {}


    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
