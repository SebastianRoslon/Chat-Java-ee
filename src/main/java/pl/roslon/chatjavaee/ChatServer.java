package pl.roslon.chatjavaee;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.HashMap;

public class ChatServer {

    static final HashMap<String, PrintWriter> connectedClients = new HashMap<>();
    static final HashMap<String, String> chatRooms = new HashMap<>();
    private static final int MAX_CONNECTED = 50;
    private static final int PORT = 10000;
    private static ServerSocket listener;

    // Aplikacja startuje z endpointu
//    public static void main(String[] args) throws IOException {
//        start();
//    }

  //  ClientHandler clientHandler;

    public static void start() {
        try {
            listener = new ServerSocket(PORT);

            System.out.println("Server start on port: " + PORT);
            System.out.println("Waiting on connection...");

            while (true) {
                if (connectedClients.size() <= MAX_CONNECTED) {
                    Thread newClient = new Thread(
                            new ClientHandler(listener.accept()));
                    newClient.start();
                }
            }
        } catch (BindException e) {
        } catch (Exception e) {
            System.out.println("\nException : \n");
            System.out.println(e);
            System.out.println("\nExiting...");
        }
    }

    public static void stop() throws IOException {
        if (!listener.isClosed()) {
            listener.close();
        }
    }
}
