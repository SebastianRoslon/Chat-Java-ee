package pl.roslon.chat.message.repository.entity;

import lombok.Setter;
import pl.roslon.chat.message.repository.entity.MessageEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class MessageRepository {

    @Setter
    @PersistenceContext
    EntityManager entityManager;

    public MessageEntity save(MessageEntity messageEntity) {
        entityManager.persist(messageEntity);
        return messageEntity;
    }

    public List<MessageEntity> getByClient(String name){
        return entityManager.createNamedQuery(MessageEntity.GET_MESSAGES_BY_CLIENT, MessageEntity.class).setParameter("name", name).getResultList();
    }

    public List<MessageEntity>getByRoom(String room){
        return entityManager.createNamedQuery(MessageEntity.GET_MESSAGES_BY_ROOM, MessageEntity.class).setParameter("room", room).getResultList();
    }

    public Optional<MessageEntity> getById(String id) {
        return Optional.ofNullable(entityManager.find(MessageEntity.class, id));
    }


}
