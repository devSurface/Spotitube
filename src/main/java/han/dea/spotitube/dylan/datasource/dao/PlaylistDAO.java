package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.datasource.ConnectionManager;
import han.dea.spotitube.dylan.datasource.datamappers.PlaylistDataMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {
    private PlaylistDataMapper dataMapper = new PlaylistDataMapper();


    public ArrayList<PlaylistDTO> getAll() {
        ArrayList<PlaylistDTO> response = new ArrayList<PlaylistDTO>();

        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT id, owner_id, name FROM playlist");
            ResultSet result = statement.executeQuery();

            response = dataMapper.MapResultSetToDTO(result);
        } catch (SQLException exception) {
            System.out.print(exception);
        }

        return response;
    }

    public void create(PlaylistDTO playlist) {
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                String.format("INSERT INTO playlist (owner_id, name) VALUES (%s, %s)",
                    playlist.getOwner(),
                    playlist.getName()
                )
            );
        }
        catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public void update(PlaylistDTO playlist) {
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                String.format("UPDATE playlist SET name = %s WHERE id = %s", playlist.getName(), playlist.getId())
            );
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public void delete(int playlistId) {
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement playlistStatement = connection.prepareStatement(
                String.format("DELETE * FROM playlist WHERE id = %s", playlistId)
            );

            PreparedStatement playlistTrackStatement = connection.prepareStatement(
                String.format("DELETE * FROM playlist_track WHERE playlist_id = %s", playlistId)
            );

            playlistStatement.execute();
            playlistTrackStatement.execute();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    public int getTotalDuration(PlaylistDTO playlist) {
        int duration = 0;
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                String.format("SELECT SUM(t.duration) AS `duration` " +
                                "FROM track t " + "INNER JOIN playlist_track pt " +
                                "ON pt.track_id = t.id " +
                                "WHERE pt.playlist_id = %s ",
                        playlist.getId()
                )
            );

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                duration += result.getInt("duration");
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }

        return duration;
    }
}