package configuration;

import lombok.Getter;
import lombok.extern.java.Log;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.NamingException;
import java.util.logging.Level;

@Log
public class ServerConnector {

    private static final String MESSAGES_TOPIC_JNDI_NAME = "jms/topic/Messages";
    private static final String CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";

    @Getter
    private ConnectionFactory connectionFactory;
    @Getter
    private JMSContext jmsContext;
    @Getter
    private Topic topic;

    public boolean connectWithServer() {
        try {
            ProxyFactory proxyFactory = new ProxyFactory();
            this.connectionFactory = proxyFactory.createProxy(CONNECTION_FACTORY_JNDI_NAME);
            this.jmsContext = connectionFactory.createContext();
            this.topic = proxyFactory.createProxy(MESSAGES_TOPIC_JNDI_NAME);
            log.log(Level.INFO, "connected with server");
            return true;
        } catch (NamingException e) {
            log.log(Level.INFO, "connected error");
            return false;
        }
    }

}
