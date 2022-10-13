package han.dea.spotitube.dylan.datasource.dao;


import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.datasource.ConnectionManager;
import han.dea.spotitube.dylan.datasource.datamappers.TrackDataMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO {
    private ConnectionManager connectionManager;
    private TrackDataMapper dataMapper;
    public ArrayList<TrackDTO> getAllTracksInPlaylist(int playlistId) {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable FROM track t JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?");
            statement.setInt(1, playlistId);

            ResultSet result = statement.executeQuery();
            ArrayList<TrackDTO> response = dataMapper.MapResultSetToDTO(result);
            return response;
        } catch (SQLException exception) {
            System.out.print(exception);
            throw new BadRequestException(exception);
        }
    }

    public ArrayList<TrackDTO> getAvailableTracks(int playlistId) {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM track t WHERE t.id not in (SELECT t.id FROM track t JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?)");
            statement.setInt(1, playlistId);

            ResultSet result = statement.executeQuery();
            ArrayList<TrackDTO> response = dataMapper.MapResultSetToDTO(result);
            return response;
        } catch (SQLException exception) {
            System.out.print(exception);
            throw new BadRequestException(exception);
        }
    }

    public void addTrackToPlaylist(int playlistId, TrackDTO track) {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?)");
            statement.setInt(1, playlistId);
            statement.setInt(2, track.getId());

            PreparedStatement statement2 = con.prepareStatement("UPDATE track SET offlineAvailable = ? WHERE id = ?");
            statement2.setBoolean(1, track.getOfflineAvailable());
            statement2.setInt(2, track.getId());


            statement.execute();
            statement2.executeUpdate();
        } catch (SQLException exception) {
            System.out.print(exception);
            throw new BadRequestException(exception);
        }
    }

    public void removeTrackFromPlaylist(int playlistId, int trackId) {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?");
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);

            statement.execute();
        } catch (SQLException exception) {
            System.out.print(exception);
            throw new BadRequestException(exception);
        }
    }
    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Inject
    public void setDataMapper(TrackDataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }


}