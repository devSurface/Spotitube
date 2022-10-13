package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import han.dea.spotitube.dylan.datasource.ConnectionManager;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
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
    // Dummy data voor playlist
    private final int PLAYLISTID = 1;
    private final String PLAYLISTNAME = "Test Song";
    private final boolean OWNER = true;
    private final int LENGTH = 100;

    private PlaylistDAO playlistDAO;
    private ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        playlistDAO = new PlaylistDAO();
        playlistDAO.setConnectionManager(connectionManager);
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
}
//