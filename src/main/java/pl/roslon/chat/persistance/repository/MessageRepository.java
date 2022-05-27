package pl.roslon.chat.persistance.repository;

import pl.roslon.chat.entity.MessageEntity;
import pl.roslon.chat.jms.ChatEntityManagerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class MessageRepository {

    @Inject
    private ChatEntityManagerFactory entityManagerFactory;

    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void saveMessage(MessageEntity messageEntity) {
        entityManager.getTransaction().begin();
        entityManager.persist(messageEntity);
        entityManager.getTransaction().commit();
    }

    public List<MessageEntity> findMessagesByRoomName(String roomName) {
        return (List<MessageEntity>) entityManager.find(MessageEntity.class, roomName);
    }


}
