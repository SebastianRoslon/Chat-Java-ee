package client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.Singleton;


@Singleton
@AllArgsConstructor
public class Client {

    @Getter
    private String name;
    @Getter
    @Setter
    private String roomName;

    public Client(String name) {
        this.name = name;
    }

}
