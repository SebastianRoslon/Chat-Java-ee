package pl.roslon.chat.repository;

import lombok.Setter;
import pl.roslon.chat.repository.entity.MessageEntity;
import pl.roslon.chat.service.MessageService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class MessageRepository {

    @Setter
    @PersistenceContext
    EntityManager entityManager;

    public MessageEntity save(MessageEntity messageEntity) {
        entityManager.persist(messageEntity);
        return messageEntity;
    }

    public Optional<MessageEntity> getById(String id) {
        return Optional.ofNullable(entityManager.find(MessageEntity.class, id));
    }


}
