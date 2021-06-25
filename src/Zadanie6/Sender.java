package Zadanie6;

import javax.naming.*;
import javax.jms.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Sender {

    public static void main(String[] args) throws NamingException, JMSException {
        System.out.print("Enter your nick: ");
        Scanner scanner = new Scanner(System.in);
        String nick = scanner.nextLine();
//        Gui gui = new Gui("Sender", true);
//        String nick = gui.getNick();
//        System.out.println("Entered nick " + nick);
        Connection connection = null;
        Context context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        String name = "queue1";
        Destination dest = (Destination) context.lookup(name);
        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer messageProducer = session.createProducer(dest);
        connection.start();
        while (true){
            String msg = scanner.nextLine();
            TextMessage textMessage = session.createTextMessage();
//            nick = gui.getNick();
            textMessage.setText(nick + ":" + msg);
//            textMessage.setText(nick + ":" + gui.getMessage());
            messageProducer.send(textMessage);
            System.out.println(nick + ": " + msg);
        }

    }
}