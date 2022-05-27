package message;

import client.ClientMessage;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.logging.Level;

@AllArgsConstructor
@Log
public class MessageSender {

    private JMSContext jmsContext;
    private Topic topic;

    public void sendMessage(ClientMessage clientMessage) {
        try {
            TextMessage textMessage = jmsContext.createTextMessage(clientMessage.convertToText());
            textMessage.setJMSCorrelationID(clientMessage.getRoomName());
            jmsContext.createProducer().send(topic, textMessage);
        } catch (JMSException e) {
            log.log(Level.INFO, "message not send");

        }
    }

}
