package pl.roslon.chat.web.api;

import pl.roslon.chat.service.MessageService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/chats/messages")
public class MessageController {
    private static final Logger LOGGER = Logger.getLogger(MessageController.class.getName());

    @Inject
    MessageService messageService;

    @GET
    @Produces("text/plain")
    public String createMessage(@QueryParam("message") String message) {
        LOGGER.info("create(" + message + ")");
        messageService.create(message);
        return "Hello, World!";
    }

    @GET
    @Path("{id}")
    public String getById(@PathParam("id") String id) {
        return String.valueOf(messageService.getById(id));
    }

    @GET
    @Produces("text/plain")
    @Path("{roomName}")
    public String createPrivMessage(@PathParam("roomName") String roomName,
                                    @QueryParam("message") String message) {
        LOGGER.info("create(" + message + ")");
        messageService.create(message);
        return message;
    }


}
