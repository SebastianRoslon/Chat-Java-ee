package client;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientMessageDto implements Serializable {

    private String roomName;
    private String userName;
    private String text;
    private String sendDate;

}
