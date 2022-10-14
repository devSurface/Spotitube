package han.dea.spotitube.dylan.datasource.datamappers;

import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDataMapper<T> {
    ArrayList<T> MapResultSetToDTO(ResultSet rs) throws SQLException;
    T MapToDTO(ResultSet rs) throws SQLException;

}
