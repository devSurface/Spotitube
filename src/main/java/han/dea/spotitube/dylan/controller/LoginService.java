package han.dea.spotitube.dylan.controller;

import han.dea.spotitube.dylan.controllers.controller.LoginController;
import han.dea.spotitube.dylan.controllers.dto.UserDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginService {
    private LoginController loginController;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO request)
    {
        try {
            return Response
                .ok(loginController.authenticate(request)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
        }
    }


    @Inject
    public void setLoginController(LoginController loginController)
    {
        this.loginController = loginController;
    }
}
