package Veiculo;
import java.util.Date;
import javax.jms.*;
import javax.swing.JOptionPane;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ProducerVeiculo {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "FILA_VEICULO"; 
    private static void envia(String msg) throws JMSException {
    	
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(
                  msg +subject + " em " + new Date().toLocaleString());
        producer.send(message); 
        System.out.println("Mensagem enviada!");
        connection.close();
    }
    public static void main(String[] args) throws JMSException {
    	Veiculo veic = new Veiculo();
    	veic.setNomeCliente(JOptionPane.showInputDialog(null, "Nome do Cliente:")); 
    	veic.setMarcaVeiculo(JOptionPane.showInputDialog(null, "Marca do Veículo:")); 
    	veic.setAno(JOptionPane.showInputDialog(null, "Ano do Veículo:")); 
    	veic.setValorVenda(JOptionPane.showInputDialog(null, "Valor da Venda:")); 

    	String msg = "\n" + "Nome do Cliente: " + veic.getNomeCliente() + 
    					"\nMarca do Veículo: " + veic.getMarcaVeiculo() +
    					"\nAno do Veículo: " + veic.getAno() +
    					"\nValor da Venda: " + veic.getValorVenda() +
    					"\n";
    	if (args != null && args.length>0 ) {    		
    	   msg = args[0] + "  \n FILA = ";    	}
        envia(msg);
    }
}
