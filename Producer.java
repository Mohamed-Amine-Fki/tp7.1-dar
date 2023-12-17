package org.example;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class Producer {
    public static void main(String[] args) {
        try {
            // Etablissement d'une connexion au broker ActiveMQ
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Creation d'une session
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic("myTopic.topic");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("Hello World");

            producer.send(textMessage);

            // Commit the transaction
            session.commit();

            System.out.println("Envoi du Message....");

            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}