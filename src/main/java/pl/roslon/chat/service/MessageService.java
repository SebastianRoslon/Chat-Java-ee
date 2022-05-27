package pl.roslon.chat.service;

import pl.roslon.chat.entity.MessageEntity;
import pl.roslon.chat.persistance.repository.MessageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MessageService {

    @Inject
    private MessageRepository messageRepository;

    public void saveMessage(MessageEntity messageEntity) {
        messageRepository.saveMessage(messageEntity);
    }

    public List<MessageEntity> getMessagesByRoomName(String roomName) {
        return messageRepository.findMessagesByRoomName(roomName);
    }

}
