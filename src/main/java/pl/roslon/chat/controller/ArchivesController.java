package pl.roslon.chat.controller;

import pl.roslon.chat.entity.MessageEntity;
import pl.roslon.chat.persistance.dto.ClientMessageDto;
import pl.roslon.chat.service.MessageService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/archives")
public class ArchivesController {

    @Inject
    private MessageService clientMessageService;

    @GET
    @Path("{roomName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomHistory(@PathParam("roomName") String roomName) {
        List<ClientMessageDto> clientMessages = clientMessageService.getMessagesByRoomName(roomName).stream()
                .map(MessageEntity::toDto)
                .toList();
        return Response.ok(clientMessages).build();
    }

}
