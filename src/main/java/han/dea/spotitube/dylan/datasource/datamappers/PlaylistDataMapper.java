package han.dea.spotitube.dylan.datasource.datamappers;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDataMapper {
    public ArrayList<PlaylistDTO> MapResultSetToDTO(ResultSet rs) throws SQLException {
        ArrayList<PlaylistDTO> playlists = new ArrayList<PlaylistDTO>();
        while (rs.next()) {
            PlaylistDTO playlist = new PlaylistDTO (
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("owner_id"),
                rs.getInt("owner_id") == 0 ? false : true
            );

            playlists.add(playlist);
        }

        return playlists;
    }
}
