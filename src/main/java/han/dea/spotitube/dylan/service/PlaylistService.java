package han.dea.spotitube.dylan.service;

import han.dea.spotitube.dylan.controllers.controller.LoginController;
import han.dea.spotitube.dylan.controllers.controller.PlaylistController;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistService {
    private LoginController loginController;
    private PlaylistController playlistController;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllPlaylist(@QueryParam("token") String token){
        try {
            loginController.verifyToken(token);
            return Response.ok(playlistController.getAllPlaylists()).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
    public void deletePlaylist() {}
    public void addPlaylist() {}
    public void editPlaylist() {}

    @Inject
    private void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Inject
    private void setPlaylistController(PlaylistController playlistController) {
        this.playlistController = playlistController;
    }
}
