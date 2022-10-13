package han.dea.spotitube.dylan.service;

import han.dea.spotitube.dylan.controllers.controller.LoginController;
import han.dea.spotitube.dylan.controllers.controller.PlaylistController;
import han.dea.spotitube.dylan.controllers.controller.TrackController;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tracks")
public class TrackService {
    private LoginController loginController;
    private TrackController trackController;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        try {
            loginController.verifyToken(token);
            return Response.ok(trackController.getAvailableTracks(playlistId)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
    public void addTrackToPlaylist() {}
    public void removeTrackFromPlaylist() {}

    @Inject
    private void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Inject
    private void setTrackController(TrackController trackController) {
        this.trackController = trackController;
    }



}
