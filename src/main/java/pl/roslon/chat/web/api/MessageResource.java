package pl.roslon.chat.web.api;

import pl.roslon.chat.service.MessageService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/chats/messages")
public class MessageResource {
    private static final Logger LOGGER = Logger.getLogger(MessageResource.class.getName());

    @Inject
    MessageService messageService;

    @GET
    @Produces("text/plain")
    public String create(@QueryParam("message") String message) {
        LOGGER.info("create(" + message + ")");
        messageService.create(message);
        return "Hello, World!";
    }
}
