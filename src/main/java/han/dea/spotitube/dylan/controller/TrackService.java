package han.dea.spotitube.dylan.controller;

import han.dea.spotitube.dylan.controllers.controller.LoginController;
import han.dea.spotitube.dylan.controllers.controller.TrackController;
import han.dea.spotitube.dylan.controllers.dto.TrackDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class TrackService {
    private LoginController loginController;
    private TrackController trackController;

    @Path("/tracks")
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
    @Path("playlists/{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(TrackDTO track, @QueryParam("token") String token, @PathParam("id") int playlistId) {
        System.out.println(track.toString());
        try {
            loginController.verifyToken(token);
            return Response.ok(trackController.addTrackToPlaylist(playlistId, track)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

    @Path("playlists/{id}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId, @PathParam("trackId") int trackId) {
        try {
            loginController.verifyToken(token);
            return Response.ok(trackController.removeTrackFromPlaylist(playlistId, trackId)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
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
