package han.dea.spotitube.dylan.controllers.controller;

import han.dea.spotitube.dylan.controllers.dto.LoginResponseDTO;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import han.dea.spotitube.dylan.datasource.dao.UserDAO;
import jakarta.inject.Inject;

public class LoginController {
    private UserDAO userDAO;

    public LoginResponseDTO authenticate(UserDTO user) throws UnauthorizedException {
        LoginResponseDTO responseDTO = userDAO.authenticate(user);
        if (responseDTO != null) {
            return responseDTO;
        } else {
            throw new UnauthorizedException();
        }
    }

    public UserDTO getUserBasedOnToken(String token)  {
        return userDAO.getUserBasedOnToken(token);
    }
    public boolean verifyToken(String token) throws UnauthorizedException {
        if (userDAO.verifyToken(token)) {
            return true;
        } else {
            throw new UnauthorizedException();
        }
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
