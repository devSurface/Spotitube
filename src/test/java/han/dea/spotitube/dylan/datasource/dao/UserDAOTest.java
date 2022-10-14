package han.dea.spotitube.dylan.datasource.dao;

import han.dea.spotitube.dylan.controllers.dto.LoginResponseDTO;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import han.dea.spotitube.dylan.datasource.dbconnection.ConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private ResultSet resultSet;

    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        preparedStatement2 = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        userDTO = mock(UserDTO.class);
        userDAO = new UserDAO();
        userDAO.setConnectionManager(connectionManager);
        userDAO = spy(userDAO);
    }

    @Test
    public void authenticateSuccessWithToken() throws SQLException, UnauthorizedException {
        // Arrange
        String expectedQuery = "SELECT * FROM user WHERE username = ? and password = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);

        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true);
        Mockito.when(resultSet.getString("token")).thenReturn("test").thenReturn("test");
        Mockito.when(resultSet.getString("token")).thenReturn("test");

        UserDTO fakeUser = new UserDTO();
        fakeUser.setUser("dylan");
        fakeUser.setPassword("dylan");

        // Act
        LoginResponseDTO actual = userDAO.authenticate(userDTO);
        // Assert
        assertEquals("test", actual.getToken());
    }

    @Test
    public void authenticateFail() throws SQLException, UnauthorizedException {
        // Arrange
        String expectedQuery = "SELECT * FROM user WHERE username = ? and password = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> userDAO.authenticate(userDTO));
    }

    @Test
    public void addToken() throws SQLException, UnauthorizedException {
        // Arrange
        String expectedQuery = "UPDATE user SET token = ? WHERE username = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        // Act
        LoginResponseDTO actual = userDAO.addToken(userDTO);
        // Assert
        assertEquals(36, actual.getToken().length());
    }

    @Test
    public void addTokenThrowsException() throws SQLException, UnauthorizedException {
        // Arrange
        String expectedQuery = "UPDATE user SET token = ? WHERE username = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenThrow(new SQLException());

        // Act
        // Assert
        assertThrows(UnauthorizedException.class, () -> userDAO.addToken(userDTO));
        verify(userDAO, times(1)).addToken(userDTO);
    }

    @Test
    public void verifyToken() throws SQLException, UnauthorizedException {
        // Arrange
        String expectedQuery = "SELECT username from user WHERE token = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);

        // Act & Assert
        assertEquals(true, userDAO.verifyToken("test"));
    }

    @Test
    public void verifyTokenFails() throws SQLException, UnauthorizedException {
        // Arrange
        String expectedQuery = "SELECT username from user WHERE token = ?";
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> userDAO.verifyToken("test"));
        verify(userDAO, times(1)).verifyToken("test");
    }

    @Test
    public void getUserBasedOnToken () {
        
    }
}
