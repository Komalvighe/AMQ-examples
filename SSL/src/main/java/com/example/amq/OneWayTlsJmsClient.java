package com.example.amq;

import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class OneWayTlsJmsClient {
    public static void main(String[] args) throws Exception {
        // Client trusts the broker via truststore; client certificate is not required.
        String url = "tcp://broker.example.com:61616";

        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
             Connection connection = factory.createConnection("jmsuser", "CHANGE_ME")) {
            connection.setExceptionListener(e -> System.err.println("JMS exception: " + e.getMessage()));
            connection.start();
            try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                 MessageConsumer consumer = session.createConsumer(session.createQueue("orders"))) {
                System.out.println("One-way TLS connected; waiting on orders...");
                while (true) {
                    Message message = consumer.receive(5000);
                    if (message instanceof TextMessage text) System.out.println("Received: " + text.getText());
                    else if (message != null) System.out.println("Received JMS message: " + message);
                }
            }
        }
    }
}
