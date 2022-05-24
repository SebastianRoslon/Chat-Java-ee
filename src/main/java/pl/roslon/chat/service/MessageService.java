package pl.roslon.chat.service;
import pl.roslon.chat.repository.entity.MessageEntity;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
public class MessageService {
    private static final Logger LOGGER = Logger.getLogger(MessageService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    public void create(String message) {
        LOGGER.info("create(" + message + ")");
        LOGGER.info("entityManager: " + entityManager.getProperties());

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);

        try {
            userTransaction.begin();
            entityManager.persist(messageEntity);
            userTransaction.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to persist Entity", e);
//            userTransaction.rollback();
        }
    }


}
