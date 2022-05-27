package pl.roslon.chat.controller;

import pl.roslon.chat.service.FileService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Path("file")
public class FileController {

    @Inject
    FileService fileService;

    @GET
    @Path("{roomName}/{fileName}")
    public Response sendFileToClient(@PathParam("roomName") String roomName,
                                     @PathParam("fileName") String fileName) {
        try {
            return Response.ok(fileService.getFile(roomName, fileName)).build();
        } catch (FileNotFoundException e) {
            return Response.status(500, "File not found.").build();
        }
    }

    @POST
    @Path("{roomName}")
    public Response downloadFileFromClient(@PathParam("roomName") String roomName,
                                           @QueryParam("fileName") String fileName,
                                           InputStream inputStream) {
        boolean fileSaved = fileService.saveFile(roomName, fileName, inputStream);
        if (fileSaved) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

}
