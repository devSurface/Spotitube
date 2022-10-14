package han.dea.spotitube.dylan.datasource.datamappers;

import han.dea.spotitube.dylan.controllers.dto.TrackDTO;

import javax.ws.rs.BadRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDataMapper implements IDataMapper {
    @Override
    public ArrayList<TrackDTO> MapResultSetToDTO(ResultSet rs) throws SQLException {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        while (rs.next()) {
            TrackDTO track = new TrackDTO (
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("performer"),
                rs.getString("album"),
                rs.getDate("publicationDate") != null ? rs.getDate("publicationDate").toString() : null,
                rs.getString("description"),
                rs.getInt("duration"),
                rs.getInt("playCount"),
                rs.getBoolean("offlineAvailable")
            );

            tracks.add(track);
        }

        return tracks;
    }

    @Override
    public Object MapToDTO(ResultSet rs) throws SQLException {
        throw new BadRequestException("Not implemented");
    }
}
