package client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClientMessage {

    @Getter
    private String roomName;
    private String senderName;
    private String message;

    public String convertToText() {
        return senderName + ": " + message;
    }

}
