package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.LoginResponseDTO;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import han.dea.spotitube.dylan.datasource.ConnectionManager;
import han.dea.spotitube.dylan.datasource.datamappers.UserDataMapper;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDAO
{
    private UserDataMapper userDataMapper;

    @Inject
    public void setUserDataMapper(UserDataMapper userDataMapper) {
        this.userDataMapper = userDataMapper;
        System.out.print("UserDAO: UserDataMapper injected");
    }
    public LoginResponseDTO authenticate(UserDTO user) throws UnauthorizedException {
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ? and password = ?");
            statement.setString(1, user.getUser());
            statement.setString(2, user.getPassword());

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                throw new UnauthorizedException();
            } else {
                if (result.getString("token") != null) {
                        return new LoginResponseDTO(result.getString("token"), result.getString("username"));
                } else {
                    return addToken(user);
                }
            }

        }
        catch (SQLException e) {
            System.out.println(e);
        }

        throw new UnauthorizedException();
    }


    private LoginResponseDTO addToken(UserDTO user) throws UnauthorizedException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String randomToken = UUID.randomUUID().toString();
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET token = ? WHERE username = ?");
            statement.setString(1, randomToken);
            statement.setString(2, user.getUser());

            statement.executeUpdate();
            return new LoginResponseDTO(user.getUser(), randomToken);
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        throw new UnauthorizedException();
    }
//
//    public boolean verifyToken(String token) throws UnauthorizedException
//    {
//        User user = new User();
//
//        try (Connection connection = dataSource.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(FETCH_USER_BY_TOKEN_QUERY);
//            statement.setString(1, token);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                user.setId(resultSet.getInt("id"));
//                user.setName(resultSet.getString("name"));
//                user.setToken(token);
//            }
//        }
//        catch (SQLException exception) {
//            logger.error("Er is iets misgegaan bij de query, fout: {}", exception.toString());
//        }
//
//        if (user.getName() != null) {
//            return user;
//        }
//
//        throw new UnauthorizedException();
//    }
}