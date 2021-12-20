package Veiculo;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerVeiculo {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL; // "vm://localhost";
    private static String subject = "FILA_VEICULO";
    private static void consumir() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive(15000);

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Recebi da Fila = '" + textMessage.getText() + "'");
        } else {
            System.out.println("** SEM MENSAGENS **  ");
        }
        consumer.close();
        session.close();
        connection.close();
    }
    public static void main(String[] args) throws JMSException {
        while (true)  consumir();  
    }
}
