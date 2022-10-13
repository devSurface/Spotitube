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
        try (Connection con = ConnectionManager.getConnection()) {
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
        try (Connection con = ConnectionManager.getConnection()) {
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

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Inject
    public void setDataMapper(TrackDataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }


}