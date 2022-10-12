package han.dea.spotitube.dylan.datasource.datamappers;

import han.dea.spotitube.dylan.controllers.dto.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDataMapper {
    public ArrayList<TrackDTO> MapResultSetToDTO(ResultSet rs) throws SQLException {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        while (rs.next()) {
            TrackDTO track = new TrackDTO (
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("performer"),
                rs.getString("album"),
                rs.getDate("publicationDate") == null ? null : null,
                rs.getString("description"),
                rs.getInt("duration"),
                rs.getInt("playCount"),
                rs.getBoolean("offlineAvailable")
            );

            tracks.add(track);
        }

        return tracks;
    }
}
