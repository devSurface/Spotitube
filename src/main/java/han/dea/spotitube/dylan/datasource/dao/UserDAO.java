package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.LoginResponseDTO;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import han.dea.spotitube.dylan.datasource.ConnectionManager;
import han.dea.spotitube.dylan.datasource.datamappers.UserDataMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDAO
{
    private ConnectionManager connectionManager;
    private UserDataMapper userDataMapper;

    public LoginResponseDTO authenticate(UserDTO user) throws UnauthorizedException {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ? and password = ?");
            statement.setString(1, user.getUser());
            statement.setString(2, user.getPassword());

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                throw new UnauthorizedException();
            } else {
                if (result.getString("token") != null) {
                    return new LoginResponseDTO(result.getString("username"), result.getString("token"));
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


    LoginResponseDTO addToken(UserDTO user) throws UnauthorizedException {
        try (Connection connection = connectionManager.getConnection()) {
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

    public boolean verifyToken(String token) throws UnauthorizedException
    {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT username from user WHERE token = ?");
            statement.setString(1, token);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                throw new UnauthorizedException();
            }
        }
        catch (SQLException exception) {
            System.out.println(exception);
        }


        return false;
    }


    @Inject
    public void setUserDataMapper(UserDataMapper userDataMapper) {
        this.userDataMapper = userDataMapper;
        System.out.print("UserDAO: UserDataMapper injected");
    }

    public UserDTO getUserBasedOnToken(String token) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE token = ?");
            statement.setString(1, token);

            ResultSet resultSet = statement.executeQuery();

            return userDataMapper.MapToDTO(resultSet);
        }
        catch (SQLException exception) {
            System.out.println(exception);
            throw new BadRequestException();
        }

    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}