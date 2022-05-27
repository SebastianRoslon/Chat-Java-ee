package pl.roslon.chat.entity;

import pl.roslon.chat.persistance.dto.ClientMessageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ChatMessage_SEQ")
    @SequenceGenerator(name = "ChatMessage_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String roomName;

    @Column
    private String userName;

    @Column
    private String text;


    public MessageEntity(String roomName, String userName, String text) {
        this.roomName = roomName;
        this.userName = userName;
        this.text = text;

    }

    public ClientMessageDto toDto() {
        ClientMessageDto clientMessageDto =  new ClientMessageDto();
        clientMessageDto.setRoomId(this.roomName);
        clientMessageDto.setUserName(this.userName);
        clientMessageDto.setText(this.text);
        return clientMessageDto;
    }

}
