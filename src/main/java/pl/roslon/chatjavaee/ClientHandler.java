package pl.roslon.chatjavaee;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Data
public class ClientHandler implements Runnable {
    private final FileSender fileSender = new FileSender(null);
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    private String room;

    public ClientHandler(Socket socket) {
        this.fileSender.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Connected " + fileSender.socket.getInetAddress());
        try {
            in = new BufferedReader(new InputStreamReader(fileSender.socket.getInputStream()));
            out = new PrintWriter(fileSender.socket.getOutputStream(), true);

            while (true) {
                out.println("Enter user name: ");
                name = in.readLine();
                if (name == null) {
                    return;
                }
                synchronized (ChatServer.connectedClients) {
                    if (!name.isEmpty() && !ChatServer.connectedClients.keySet().contains(name)) {
                        break;
                    } else {
                        out.println("Username is ");
                    }
                }
            }

            out.println("Enter room name (public is deflaut)");
            room = in.readLine();
            room = room.toUpperCase();
            ChatServer.chatRooms.put(name, room);

            out.println("Assigned as " + name);
            out.println("Assignet to room " + room);
            System.out.println(name + " is assinging to room " + room);
            MessageService.broadcastMessage(name + " is assinging to room " + room, room);

            ChatServer.connectedClients.put(name, out);
            MessageService.printHistoryMessages(room, out);
            out.println("Now you can sending messages. To see help type: /help");

            String message;
            label:
            while ((message = in.readLine()) != null) {
                if (!message.isEmpty()) {
                    switch (message.trim()) {
                        case "/help":
                            showHelp();
                            break;
                        case "/send-file":
                            if (fileSender.sendFile())
                                continue;
                            break;
                        case "/change-room":
                            changeRoom();
                            break;
                        case "/exit":
                            break label;
                        default:
                            MessageService.broadcastMessage(name + ": " + message, room);
                            MessageService.saveMessageToFile(name + ": " + message, room);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (name != null) {
                System.out.println(name + " is exiting  " + room);
                MessageService.broadcastMessage(name + " is exiting " + room, room);
                ChatServer.connectedClients.remove(name);
            }
        }
    }

    private void changeRoom() throws IOException {
        out.println("Exit room " + room + ".");
        System.out.println(name + " is exiting room " + room);
        MessageService.broadcastMessage(name + " is exiting room " + room, room);

        out.println("Enter room name ('public', is default):");
        room = in.readLine();
        room = room.toUpperCase();
        ChatServer.chatRooms.put(name, room);

        out.println("Is in room " + room + ".");
        System.out.println(name + " is in room " + room);
        MessageService.broadcastMessage(name + " is in room " + room, room);

        MessageService.printHistoryMessages(room, out);
    }

    private void showHelp() {
        out.println("Command list:\n/help - show command list\n/send-file - sending file\n/change-room - change room\n/exit - exit");
    }
}

