package han.dea.spotitube.dylan.service;

import han.dea.spotitube.dylan.controllers.controller.LoginController;
import han.dea.spotitube.dylan.controllers.controller.PlaylistController;
import han.dea.spotitube.dylan.controllers.controller.TrackController;
import han.dea.spotitube.dylan.controllers.dto.PlaylistDTO;
import han.dea.spotitube.dylan.controllers.exceptions.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class PlaylistService {
    private LoginController loginController;
    private PlaylistController playlistController;
    private TrackController  trackController;

    @Path("playlists")
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


    @DELETE
    @Path("playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId) {
        try {
            loginController.verifyToken(token);
            return Response.ok(playlistController.deletePlaylist(playlistId)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
    @POST
    @Path("playlists")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlist) {
        try {
            loginController.verifyToken(token);
            return Response.ok(playlistController.addPlaylist(playlist, token)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

    @PUT
    @Path("playlists/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, PlaylistDTO playlist) {
        try {
            loginController.verifyToken(token);
            return Response.ok(playlistController.editPlaylist(playlist, token)).build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }


    @Inject
    private void setTrackController(TrackController trackController) {
        this.trackController = trackController;
    }
    @Inject
    private void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Inject
    private void setPlaylistController(PlaylistController playlistController) {
        this.playlistController = playlistController;
    }
}
