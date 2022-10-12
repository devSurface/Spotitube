package han.dea.spotitube.dylan.datasource.datamappers;

import han.dea.spotitube.dylan.controllers.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataMapper {
    public UserDTO MapToDTO(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UserDTO user = new UserDTO(
                    rs.getString("username"),
                    rs.getString("password")
            );

            return user;
        } else {
            throw new SQLException("Nothing found");
        }
    }
}
