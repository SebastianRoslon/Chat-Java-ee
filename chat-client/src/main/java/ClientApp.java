
import client.Client;
import configuration.Configuration;
import configuration.ServerConnector;
import message.MessageReceiver;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {
        System.out.println("Your name: ");
        Scanner scanner = new Scanner(System.in);
        Client client = new Client(scanner.nextLine());
        client.setRoomName(Configuration.MAIN_ROOM);

        ServerConnector serverConnector = new ServerConnector();
        boolean connected = serverConnector.connectWithServer();

        if (connected) {
            MessageReceiver messageReceiver = new MessageReceiver(serverConnector.getJmsContext(), serverConnector.getTopic(), client);
            CommandService commandService = new CommandService();
            new Thread(commandService).start();
            new Thread(messageReceiver).start();
        }
    }

}
