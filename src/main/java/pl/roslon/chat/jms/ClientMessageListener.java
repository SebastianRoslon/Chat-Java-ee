package pl.roslon.chat.jms;

import pl.roslon.chat.entity.MessageEntity;
import pl.roslon.chat.service.MessageService;
import lombok.SneakyThrows;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Messages")
})
public class ClientMessageListener implements MessageListener {

    @Inject
    private MessageService clientMessageService;

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        String text = message.getBody(String.class);
        String[] textElements = text.split(": ", 2);
        MessageEntity messageEntity = new MessageEntity(message.getJMSCorrelationID(), textElements[0], textElements[1]);
        clientMessageService.saveMessage(messageEntity);
    }

}
