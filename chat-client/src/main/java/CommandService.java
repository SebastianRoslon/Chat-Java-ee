import client.Client;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.extern.java.Log;
import client.ClientMessage;
import message.MessageReceiver;
import message.MessageSender;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jgroups.util.Tuple;


import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.logging.Level;

@Log
public class CommandService implements Runnable{

    @Inject
    JMSContext jmsContext;
    private Client client;
    private Topic topic;
    private MessageReceiver messageReceiver;


    @Override
    public void run() {
        MessageSender messageSender = new MessageSender(jmsContext, topic);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (true) {
            if (scanner.hasNextLine()) {
               switcher();
                } else {
                    ClientMessage clientMessage = new ClientMessage(client.getRoomName(), client.getName(), input);
                    messageSender.sendMessage(clientMessage);
                }
            }
        }

    public void switcher() {
        String command = null;
        assert false;
        Tuple<String, String> commandAndParam = commandHelper(command);
        String param = commandAndParam.getVal2();

        Scanner scanner = new Scanner(System.in);
        String commander = scanner.nextLine();
        switch (commander) {
            case "help":
                printCommands();
                break;
            case "join_room":
                joinToRoom(client, messageReceiver, param);
                break;
            case "archives":
                printRoomArchives(client.getRoomName());
                break;
            case "download":
                download(client.getRoomName(), param);
                break;
            case "send":
                send(client.getRoomName(), param);
                break;
        }
    }

    public static Tuple<String, String> commandHelper(String message) {
        String command = "";
        String param = "";
        if (message.contains(" ")) {
            String[] commandParts = message.split(" ", 2);
            command = commandParts[0];
            param = commandParts[1];
        } else {
            command = message;
        }
        return new Tuple<>(command, param);
    }

    public String printCommands() {
        return """
                Commands:
                help
                join_room
                archives
                download
                send
                """ ;
    }

    public void printRoomArchives(String roomName) {
        var restClient = new ResteasyClientBuilderImpl().build();
        var roomHistoryMessages = restClient.target("http://localhost:8080/java-ee-jakarta/archives/" + roomName)
                .request()
                .get(String.class);
        JsonArray jsonArray = new Gson().fromJson(roomHistoryMessages, JsonArray.class);
    }

    public void download(String roomName, String fileName) {
        try {
            var restClient = new ResteasyClientBuilderImpl().build();
            var file = restClient.target("http://localhost:8080/java-ee-jakarta//file/" + roomName + "/" + fileName)
                    .request()
                    .get(File.class);
            File newFile = new File(System.getProperty("user.dir") + "\\" + fileName);
            file.renameTo(newFile);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.close();
        } catch (Exception e) {
            log.log(Level.INFO, "download error");
        }
    }

    public void send(String roomName, String fileFullPath) {
        File file = new File(fileFullPath);
        try (InputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes, 0, fileBytes.length);

            var restClient = new ResteasyClientBuilderImpl().build();
            restClient.target("http://localhost:8080/java-ee-jakarta/file/" + roomName)
                    .queryParam("fileName", getFileName(fileFullPath))
                    .request()
                    .header("Content-Length", 0)
                    .post(Entity.entity(fileInputStream, MediaType.APPLICATION_OCTET_STREAM));
        } catch (IOException e) {
            log.log(Level.INFO, "error");
        }
    }

    public void joinToRoom(Client client, MessageReceiver messageReceiver, String newRoomName) {
        client.setRoomName(newRoomName);
        messageReceiver.updateRoomName();
    }

    private static String getFileName(String file) {
        if (file.contains("\\")) {
            return file.substring(file.lastIndexOf("\\") + 1);
        }
        return file;
    }

}
