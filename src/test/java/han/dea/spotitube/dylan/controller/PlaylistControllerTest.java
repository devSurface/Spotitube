
        package han.dea.spotitube.dylan.controller;

        import han.dea.spotitube.dylan.controllers.controller.PlaylistController;
        import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
        import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
        import han.dea.spotitube.dylan.datasource.dao.PlaylistDAO;
        import han.dea.spotitube.dylan.datasource.dao.TrackDAO;
        import jakarta.ws.rs.BadRequestException;
        import org.json.simple.JSONObject;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mock;
        import org.mockito.Mockito;
        import org.mockito.MockitoAnnotations;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.List;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.isA;
        import static org.mockito.Mockito.*;

public class PlaylistControllerTest
{
    /**
     * The tests in this file are not useful (yet)
     * these tests are here so if the logic in these
     * methods is upgraded in the future, it is easily testable.
     */

    @Mock private PlaylistDAO playlistDAO;
    @Mock private TrackDAO trackDAO;

    @Mock
    private PlaylistController playlistController = new PlaylistController();
    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        playlistController.setPlaylistDAO(playlistDAO);
        playlistController.setTrackDAO(trackDAO);

        playlists.add(new PlaylistDTO(
                1,
                "Playlist 1",
                1,
                true
        ));
        playlists.add(new PlaylistDTO(
                2,
                "Playlist 2",
                1,
                true
        ));

    }

    @Test
    public void getAllSuccessful()
    {
        // Arrange
        JSONObject mockedJson = new JSONObject();
        mockedJson.put("playlists", playlists);
        mockedJson.put("length", 0);
        Mockito.when(playlistController.getAllPlaylists()).thenReturn(mockedJson);

        // Act
        JSONObject playlistsJson = playlistController.getAllPlaylists();

        // Assert
        assertEquals(mockedJson, playlistsJson);
    }

    @Test
    public void removedSuccessful()
    {
        // Arrange
        JSONObject mockedJson = new JSONObject();
        mockedJson.put("playlists", playlists);
        mockedJson.put("length", 0);
        Mockito.when(playlistController.deletePlaylist(1)).thenReturn(mockedJson);

        // Act / assert
        assertDoesNotThrow(() -> playlistController.deletePlaylist(1));
        verify(playlistController, times(1)).deletePlaylist(1);
    }

    @Test
    public void editedSuccessful()
    {
        // Arrange
        JSONObject mockedJson = new JSONObject();
        mockedJson.put("playlists", playlists);
        mockedJson.put("length", 0);

        ArrayList<PlaylistDTO> expectedOutcome = new ArrayList<>();
        expectedOutcome.add(new PlaylistDTO(
                1,
                "Playlist test",
                1,
                true
        ));
        expectedOutcome.add(new PlaylistDTO(
                2,
                "Playlist 2",
                1,
                true
        ));

        JSONObject expectedOutcomeJson = new JSONObject();
        expectedOutcomeJson.put("playlists", expectedOutcome);
        expectedOutcomeJson.put("length", 0);

        Mockito.when(playlistController.editPlaylist(expectedOutcome.get(0), "")).thenReturn(mockedJson);

        // Act / assert
        assertEquals(playlistController.editPlaylist(expectedOutcome.get(0), ""), expectedOutcomeJson);
        assertDoesNotThrow(() -> playlistController.editPlaylist(playlists.get(0), ""));
        verify(playlistController, times(1)).editPlaylist(playlists.get(0), "");
    }
//
//    @Test public void editedSuccessful()
//    {
//        // Arrange
//        PlaylistDTO playlistDTO = new PlaylistDTO();
//        Mockito.when(playlistDAO.editTitle(DUMMY_PLAYLIST.getId(), playlistDTO, DUMMY_USER.getId())).thenReturn(true);
//
//        // Act / assert
//        assertFalse(playlistService.editTitle(DUMMY_PLAYLIST.getId(), new PlaylistDTO(), DUMMY_USER.getId()));
//    }
//
//    @Test
//    public void editedPlaylistError()
//    {
//        // Arrange
//        PlaylistDTO playlistDTO = new PlaylistDTO();
//        Mockito.when(playlistDAO.editTitle(DUMMY_PLAYLIST.getId(), playlistDTO, DUMMY_USER.getId())).thenReturn(false);
//
//        // Act / assert
//        assertFalse(playlistService.editTitle(DUMMY_PLAYLIST.getId(), playlistDTO, DUMMY_USER.getId()));
//    }
//
//    @Test public void editedPlaylistSuccessful()
//    {
//        // Arrange
//        Playlist playlist = new Playlist();
//        PlaylistDTO playlistDTO = new PlaylistDTO();
//        Mockito.when(playlistDAO.editTitle(DUMMY_PLAYLIST.getId(), playlistDTO, DUMMY_USER.getId())).thenReturn(true);
//
//        // Act / assert
//        assertFalse(playlistService.editTitle(DUMMY_PLAYLIST.getId(), new PlaylistDTO(), DUMMY_USER.getId()));
//    }
//
//    @Test public void addSuccessful()
//    {
//        // Arrange
//        PlaylistDTO playlistDTO = new PlaylistDTO();
//        Mockito.when(playlistService.add(playlistDTO, DUMMY_USER.getId())).thenReturn(true);
//
//        // Act / assert
//        assertFalse(playlistService.add(playlistDTO, DUMMY_USER.getId()));
//    }
//
//    @Test public void addError()
//    {
//        // Arrange
//        PlaylistDTO playlistDTO = new PlaylistDTO();
//        Mockito.when(playlistService.add(playlistDTO, DUMMY_USER.getId())).thenReturn(false);
//
//        // Act / assert
//        assertFalse(playlistService.add(playlistDTO, DUMMY_USER.getId()));
//    }
//
//
//    @Test public void removedError()
//    {
//        // Arrange
//        Mockito.when(playlistService.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId())).thenReturn(false);
//
//        // Act / assert
//        assertFalse(playlistService.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId()));
//    }
//

}