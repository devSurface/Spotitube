package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.datasource.dbconnection.ConnectionManager;
import han.dea.spotitube.dylan.datasource.datamappers.TrackDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TrackDAOTest {
    private TrackDAO trackDAO;
    private ConnectionManager connectionManager;
    private TrackDataMapper trackDataMapper;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private ResultSet resultSet;

    private TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        connectionManager = mock(ConnectionManager.class);
        trackDataMapper = mock(TrackDataMapper.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        preparedStatement2 = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        trackDTO = mock(TrackDTO.class);
        trackDAO = new TrackDAO();
        trackDAO.setConnectionManager(connectionManager);
        trackDAO.setDataMapper(trackDataMapper);
        trackDAO = spy(trackDAO);
    }

    @Test
    public void getAllTracksInPlaylist() throws SQLException {
        // Arrange
        ArrayList<TrackDTO> mockTrackArray = new ArrayList<>();
        TrackDTO mockTrack = new TrackDTO();
        mockTrack.setId(1);
        mockTrackArray.add(mockTrack);

        String expectedQuery = "SELECT id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable FROM track t JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
        Mockito.when(trackDataMapper.MapResultSetToDTO(resultSet)).thenReturn(mockTrackArray);

        ArrayList<TrackDTO> tracks = trackDAO.getAllTracksInPlaylist(1);

        // Act & Assert
        assertEquals(tracks.get(0).getId(), 1);
    }

    @Test
    public void getAllTracksInPlaylistError() throws SQLException {
        // Arrange
        String expectedQuery = "SELECT id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable FROM track t JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenThrow(new SQLException());

        // Act / assert
        assertThrows(BadRequestException.class, () -> trackDAO.getAllTracksInPlaylist(1));
        verify(trackDAO, times(1)).getAllTracksInPlaylist(1);
    }

    @Test
    public void getAvailableTracks() throws SQLException {
        // Arrange
        ArrayList<TrackDTO> mockTrackArray = new ArrayList<>();
        TrackDTO mockTrack = new TrackDTO();
        mockTrack.setId(2);
        mockTrackArray.add(mockTrack);

        String expectedQuery = "SELECT * FROM track t WHERE t.id not in (SELECT t.id FROM track t JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?)";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
        Mockito.when(trackDataMapper.MapResultSetToDTO(resultSet)).thenReturn(mockTrackArray);

        ArrayList<TrackDTO> tracks = trackDAO.getAvailableTracks(1);

        // Act & Assert
        assertEquals(tracks.get(0).getId(), 2);
    }

    @Test
    public void getAvailableTracksError() throws SQLException {
        // Arrange
        String expectedQuery = "SELECT * FROM track t WHERE t.id not in (SELECT t.id FROM track t JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?)";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenThrow(new SQLException());

        // Act / assert
        assertThrows(BadRequestException.class, () -> trackDAO.getAvailableTracks(1));
        verify(trackDAO, times(1)).getAvailableTracks(1);
    }

    @Test
    public void addTrackToPlaylist() throws SQLException {
        // Arrange
        String expectedQuery = "INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?)";
        String expectedQuery2 = "UPDATE track SET offlineAvailable = ? WHERE id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        Mockito.when(connection.prepareStatement(expectedQuery2)).thenReturn(preparedStatement2);
        Mockito.when(preparedStatement2.executeUpdate()).thenReturn(1);

        TrackDTO mockTrack = new TrackDTO();
        mockTrack.setId(2);

        // Act &  Assert
        assertDoesNotThrow(() -> trackDAO.addTrackToPlaylist(1, mockTrack));
        verify(trackDAO, times(1)).addTrackToPlaylist(1, mockTrack);
    }

    @Test
    public void addTrackToPlaylistTrackNotFound() throws SQLException {
        // Arrange
        String expectedQuery = "INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?)";
        String expectedQuery2 = "UPDATE track SET offlineAvailable = ? WHERE id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        Mockito.when(connection.prepareStatement(expectedQuery2)).thenReturn(preparedStatement2);
        Mockito.when(preparedStatement2.executeUpdate()).thenThrow(new SQLException());

        TrackDTO mockTrack = new TrackDTO();
        mockTrack.setId(-1);

        // Act / assert
        assertThrows(BadRequestException.class, () -> trackDAO.addTrackToPlaylist(1, mockTrack));
        verify(trackDAO, times(1)).addTrackToPlaylist(1, mockTrack);
    }

    @Test
    public void removeTrackFromPlaylist() throws SQLException {
        // Arrange
        String expectedQuery = "DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act & Assert
        assertDoesNotThrow(() -> trackDAO.removeTrackFromPlaylist(1, 1));
        verify(trackDAO, times(1)).removeTrackFromPlaylist(1, 1);
    }

    @Test
    public void removeTrackFromPlaylistError() throws SQLException {
        // Arrange
        String expectedQuery = "DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.execute()).thenThrow(new SQLException());

        // Act / assert
        assertThrows(BadRequestException.class, () -> trackDAO.removeTrackFromPlaylist(1, 1));
        verify(trackDAO, times(1)).removeTrackFromPlaylist(1, 1);
    }
}
