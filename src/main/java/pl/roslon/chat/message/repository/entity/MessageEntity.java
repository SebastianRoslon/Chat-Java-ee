package pl.roslon.chat.message.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@NamedQuery(name = MessageEntity.GET_MESSAGES_BY_CLIENT, query = "SELECT m FROM chat_messages m WHERE m.clientSentBy = :name")
@NamedQuery(name = MessageEntity.GET_MESSAGES_BY_ROOM, query = "SELECT m FROM chat_messages m WHERE m.channelSentTo = :name")
@Entity(name = "chat_messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ChatMessage_SEQ")
    @SequenceGenerator(name = "ChatMessage_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    private String message;
    String clientSentBy;
    String channelSentTo;

    public static final String GET_MESSAGES_BY_CLIENT = "getMessagesByClient";
    public static final String GET_MESSAGES_BY_ROOM = "getMessagesByRoom";


    public MessageEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClientSentBy() {
        return clientSentBy;
    }

    public void setClientSentBy(String clientSentBy) {
        this.clientSentBy = clientSentBy;
    }

    public String getChannelSentTo() {
        return channelSentTo;
    }

    public void setChannelSentTo(String channelSentTo) {
        this.channelSentTo = channelSentTo;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", clientSentBy='" + clientSentBy + '\'' +
                ", channelSentTo='" + channelSentTo + '\'' +
                '}';
    }
}
