package pl.roslon.chatjavaee;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


public class FileSender {
    private final ClientHandler clientHandler;
    Socket socket;

    public FileSender(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    boolean sendFile() throws IOException {
        clientHandler.getOut().println("Enter filepath:");
        String path = clientHandler.getIn().readLine();

        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                clientHandler.getOut().println("Filepath is not correct");
                return true;
            }
        } catch (Exception e) {
            clientHandler.getOut().println("No file found");
            return true;
        }

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        FileInputStream fileInputStream = new FileInputStream(file);

        int bytes = 0;
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }

        clientHandler.getOut().println("File is send");
        MessageService.broadcastMessage(clientHandler.getName() + " sending file " + file.getName() + " on room " + clientHandler.getRoom(), clientHandler.getRoom());

        bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(file.getName());

        long size = dataInputStream.readLong();
        buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = fileInputStream.read(buffer, 0,
                (int) Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes;
        }

        fileInputStream.close();
        fileOutputStream.close();
        dataOutputStream.close();
        dataInputStream.close();
        return false;
    }
}
