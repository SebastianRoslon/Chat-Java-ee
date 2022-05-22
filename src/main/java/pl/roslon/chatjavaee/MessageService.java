package pl.roslon.chatjavaee;

import pl.roslon.chatjavaee.repository.MessageEntity;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Named
public class MessageService {
    static void saveMessageToFile(String message, String room) {
        String filePath = room + ".txt";
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filePath, true);
            fileWriter.write(message);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Resource
    private UserTransaction userTransaction;
    @PersistenceContext
    private EntityManager entityManager;

    public void saveMessageToDatabase(String message) {
        LOGGER.info("create(" + message + ")");

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);

        try {
            userTransaction.begin();
            entityManager.persist(messageEntity);
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<MessageEntity> getById(String id){
        return Optional.ofNullable(entityManager.find(MessageEntity.class, id));
    }


    public static void printHistoryMessages(String room, PrintWriter out) {
        File file = new File(room + ".txt");
        if (file.exists()) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {
                    out.println(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    static void broadcastMessage(String message, String room) {
        for (String name : ChatServer.connectedClients.keySet()) {
            if (ChatServer.chatRooms.get(name).equals(room)) {
                ChatServer.connectedClients.get(name).println(message);
            }
        }
    }

}
