package han.dea.spotitube.dylan.datasource.datamappers;

import han.dea.spotitube.dylan.controllers.dto.UserDTO;

import javax.ws.rs.BadRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDataMapper implements IDataMapper {
    @Override
    public ArrayList MapResultSetToDTO(ResultSet rs) throws SQLException {
        throw new BadRequestException("Not implemented");
    }

    @Override
    public UserDTO MapToDTO(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UserDTO user = new UserDTO(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("id")
            );

            return user;
        } else {
            throw new SQLException("Nothing found");
        }
    }


}
