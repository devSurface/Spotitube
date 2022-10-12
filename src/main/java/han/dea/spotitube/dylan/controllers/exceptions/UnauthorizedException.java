package han.dea.spotitube.dylan.controllers.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class UnauthorizedException extends Exception implements ExceptionMapper<UnauthorizedException> {
    public UnauthorizedException(){
        super("Unauthorized: Invalid credentials");
    }

    @Override
    public Response toResponse(UnauthorizedException e){
        return Response.status(401).entity(e.getMessage()).build();
    }
}