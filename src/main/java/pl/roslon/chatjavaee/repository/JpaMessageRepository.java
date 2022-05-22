//package pl.roslon.chatjavaee.repository;
//
//import lombok.Setter;
//
//import javax.ejb.Singleton;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.Optional;
//
//@Singleton
//public class JpaMessageRepository {
//
//    @Setter
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public MessageEntity save(MessageEntity messageEntity){
//        entityManager.persist(messageEntity);
//        return messageEntity;
//    }
//
//    public Optional<MessageEntity> getById(String id){
//        return Optional.ofNullable(entityManager.find(MessageEntity.class, id));
//    }
//
//
//}
