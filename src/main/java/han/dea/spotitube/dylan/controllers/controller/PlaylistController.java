package han.dea.spotitube.dylan.controllers.controller;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.datasource.dao.PlaylistDAO;
import han.dea.spotitube.dylan.datasource.dao.TrackDAO;
import jakarta.inject.Inject;
import jakarta.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaylistController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;
    private LoginController loginController;
    public JSONObject getAllPlaylists() {
        ArrayList<PlaylistDTO> playlists = playlistDAO.getAll();

        int length = 0;
        for (PlaylistDTO playlist : playlists) {
            ArrayList<TrackDTO> tracks = trackDAO.getAllTracksInPlaylist(playlist.getId());
            playlist.setTracks(tracks);

            for(TrackDTO track : playlist.getTracks()) {
                length += track.getDuration();
            }

            playlist.setOwnerId(null);
        }

        JSONObject response = new JSONObject();
        response.put("playlists", playlists);
        response.put("length", length);

        return response;
    }
    public JSONObject deletePlaylist(int playlistId) {
        playlistDAO.delete(playlistId);
        return getAllPlaylists();
    }
    public JSONObject addPlaylist(PlaylistDTO playlist, String token) {
        playlistDAO.add(playlist, loginController.getUserBasedOnToken(token));
        return getAllPlaylists();
    }
    public JSONObject editPlaylist(PlaylistDTO playlist, String token) {
        playlistDAO.update(playlist, loginController.getUserBasedOnToken(token));
        return getAllPlaylists();
    }


    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
