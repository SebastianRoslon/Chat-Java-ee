package pl.roslon.chatjavaee.controller;

import org.jboss.resteasy.tracing.RESTEasyTracingLogger;
import pl.roslon.chatjavaee.ChatServer;
import pl.roslon.chatjavaee.MessageService;
import pl.roslon.chatjavaee.client.ChatClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@javax.mvc.Controller
public class Controller {

    @Inject
    MessageService messageService;

    @Inject
    ChatServer chatServer;

    @Inject
    ChatClient chatClient;

    @GET
    @Path("/chat")
    public String startServer(){
        chatServer.start();
        chatClient.start();
        return "Server started";
    }

    @GET
    @Path("/id")
    public String getById(String id){
        messageService.getById(id);
        return "Get by id";
    }

    @GET
    @Path("/name")
    public String printName(){
        return "Stefan";
    }

}
