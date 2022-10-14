package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import han.dea.spotitube.dylan.datasource.ConnectionManager;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistDAOTest {
    private PlaylistDAO playlistDAO;
    private ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        preparedStatement2 = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        playlistDAO = new PlaylistDAO();
        playlistDAO.setConnectionManager(connectionManager);
        playlistDAO = spy(playlistDAO);
    }

    /**
     * DAO FOR:
     * [GET] /playlists
     */
    @Test
    public void getAllPlaylists() throws SQLException {
        // Arrange
        String expectedQuery = "SELECT id, owner_id, name FROM playlist";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getString("name")).thenReturn("test");
        Mockito.when(resultSet.getInt("owner_id")).thenReturn(1);

        // Act
        List<PlaylistDTO> playlists = playlistDAO.getAll();

        // Act & Assert
        assertEquals(playlists.get(0).getId(), 1);
    }


    /**
     * DAO FOR:
     * [GET] /playlists
     */
    @Test
    public void getAllException() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT id, owner_id, name FROM playlist";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenThrow(new SQLException());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> playlistDAO.getAll());
    }


    /**
     * DAO FOR:
     * [DELETE] /playlists/{id}
     */
    @Test
    public void removedSuccessful() throws SQLException {
        // Arrange
        String expectedQuery = "DELETE FROM playlist WHERE id = ?";
        String expectedQuery2 = "DELETE FROM playlist_track WHERE playlist_id = ?";

        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(expectedQuery2)).thenReturn(preparedStatement2);
        Mockito.when(preparedStatement.execute()).thenReturn(true);
        Mockito.when(preparedStatement2.execute()).thenReturn(true);


        // Act / assert
        assertDoesNotThrow(() -> playlistDAO.delete(1));
        verify(playlistDAO, times(1)).delete(1);
    }

    /**
     * DAO FOR:
     * [DELETE] /playlists/{id}
     */
    @Test
    public void removedException() throws SQLException {
        // Arrange
        String expectedQuery = "DELETE FROM playlist WHERE id = ?";
        String expectedQuery2 = "DELETE FROM playlist_track WHERE playlist_id = ?";

        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(expectedQuery2)).thenReturn(preparedStatement2);
        Mockito.when(preparedStatement.execute()).thenThrow(new SQLException());
        Mockito.when(preparedStatement2.execute()).thenThrow(new SQLException());

        // Act / assert
        assertThrows(BadRequestException.class, () -> playlistDAO.delete(1));
        verify(playlistDAO, times(1)).delete(1);
    }

    /**
     * DAO FOR:
     * [POST] /playlists
     */
    @Test
    public void addPlaylist() throws SQLException {
        // Arrange
        String expectedQuery = "INSERT INTO playlist (owner_id, name) VALUES (?, ?)";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.execute()).thenReturn(true);

        PlaylistDTO testPlaylist = new PlaylistDTO(1, "test", 1, true);
        UserDTO testUser = new UserDTO("test", "test", 1);

        // Act & Assert
        assertDoesNotThrow(() -> playlistDAO.add(testPlaylist, testUser));
        verify(playlistDAO, times(1)).add(testPlaylist, testUser);
    }

    /**
     * DAO FOR:
     * [POST] /playlists
     */
    @Test
    public void addException() throws SQLException {
        // Arrange
        String expectedQuery = "INSERT INTO playlist (owner_id, name) VALUES (?, ?)";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.execute()).thenThrow(new SQLException());

        PlaylistDTO testPlaylist = new PlaylistDTO(1, "test", 1, true);
        UserDTO testUser = new UserDTO("test", "test", 1);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> playlistDAO.add(testPlaylist, testUser));
        verify(playlistDAO, times(1)).add(testPlaylist, testUser);
    }

    /**
     * DAO FOR:
     * [PUT] /playlists/{id}
     */
    @Test
    public void updatePlaylist() throws SQLException {
        // Arrange
        String expectedQuery = "UPDATE playlist SET name = ? WHERE id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.execute()).thenReturn(true);

        PlaylistDTO testPlaylist = new PlaylistDTO(1, "test", 1, true);
        UserDTO testUser = new UserDTO("test", "test", 1);

        // Act & Assert
        assertDoesNotThrow(() -> playlistDAO.update(testPlaylist, testUser));
        verify(playlistDAO, times(1)).update(testPlaylist, testUser);
    }

    /**
     * DAO FOR:
     * [PUT] /playlists/{id}
     */
    @Test
    public void updateException() throws SQLException {
        // Arrange
        String expectedQuery = "UPDATE playlist SET name = ? WHERE id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

        PlaylistDTO testPlaylist = new PlaylistDTO(1, "test", 1, true);
        UserDTO testUser = new UserDTO("test", "test", 1);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> playlistDAO.update(testPlaylist, testUser));
        verify(playlistDAO, times(1)).update(testPlaylist, testUser);
    }
}

