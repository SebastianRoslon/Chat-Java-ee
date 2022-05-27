package message;

import client.Client;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Topic;

@Log
public class MessageReceiver implements Runnable {

    private JMSContext jmsContext;
    private Topic topic;
    @Setter
    private Client client;
    private JMSConsumer consumer;

    public MessageReceiver(JMSContext jmsContext, Topic topic, Client client) {
        this.jmsContext = jmsContext;
        this.topic = topic;
        this.client = client;
    }

    @Override
    public void run() {
        updateRoomName();
    }

    private static MessageListener onMessage = message -> {
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    };

    public void updateRoomName( ) {
        if (consumer != null) {
            consumer.close();
        }
        consumer = jmsContext.createConsumer(topic, "JMSCorrelationID = '" + client.getRoomName() + "'");
        consumer.setMessageListener(onMessage);
    }

}
