package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.datasource.dbconnection.ConnectionManager;
import han.dea.spotitube.dylan.datasource.datamappers.PlaylistDataMapper;
import jakarta.inject.Inject;
import javax.ws.rs.BadRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {
    private ConnectionManager connectionManager;
    private PlaylistDataMapper dataMapper = new PlaylistDataMapper();
    private UserDAO userDAO;

    public ArrayList<PlaylistDTO> getAll() {
        ArrayList<PlaylistDTO> response = new ArrayList<>();

        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT id, owner_id, name FROM playlist");
            ResultSet result = statement.executeQuery();

            response = dataMapper.MapResultSetToDTO(result);
        } catch (SQLException exception) {
            System.out.println(exception);
            throw new BadRequestException();
        }

        return response;
    }

    public void add(PlaylistDTO playlist, UserDTO user) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO playlist (owner_id, name) VALUES (?, ?)");

            statement.setInt(1, playlist.getOwner() ? user.getId() : 0);
            statement.setString(2, playlist.getName());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println(exception);
            throw new BadRequestException();
        }
    }

    public void update(PlaylistDTO playlist, UserDTO user) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE id = ?");
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new BadRequestException();
        }
    }

    public void delete(int playlistId) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement playlistStatement = connection.prepareStatement("DELETE FROM playlist WHERE id = ?");
            playlistStatement.setInt(1, playlistId);

            PreparedStatement playlistTrackStatement = connection.prepareStatement("DELETE FROM playlist_track WHERE playlist_id = ?");
            playlistTrackStatement.setInt(1, playlistId);

            playlistStatement.execute();
            playlistTrackStatement.execute();
        } catch (SQLException exception) {
            System.out.println(exception);
            throw new BadRequestException(exception);
        }
    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}