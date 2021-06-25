package Zadanie6;

import javax.naming.*;
import javax.jms.*;

public class Receiver {

    public static void main(String[] args) throws NamingException, JMSException {
//        Gui gui = new Gui("Receiver", false);
        Connection connection = null;
        Context context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        String name = "queue1";
        Destination destination = (Destination) context.lookup(name);
        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer messageConsumer = session.createConsumer(destination);
        connection.start();
//        gui.setMessage("");
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
//                gui.setMessage(message.toString());
                System.out.println(message.toString());
            }
        });

    }

}
