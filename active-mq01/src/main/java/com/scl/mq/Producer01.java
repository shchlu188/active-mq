package com.scl.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ScheduledMessage;
import org.graalvm.compiler.nodes.calc.PointerEqualsNode;

import javax.jms.*;
import java.util.UUID;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/6
 * @Description
 **********************************/
@Slf4j
public class Producer01 {
    private static final String DEFAULT_URL = "tcp://192.168.137.135:61616";
    private static final String DEFAULT_NAME = "QUEUE";
    private static final String MSG = "hello msg";
    private static final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_URL);
    private static Connection connection;

    static {
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        ;
    }

    public static void main(String[] args) throws JMSException {

        producer_persistent_01(6);
    }

    private static void producer_01(int count) throws JMSException {
        connection.start(); // 启动访问
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【Sender】 send msg to queue:" + MSG);
        MessageProducer producer = session.createProducer(session.createQueue(DEFAULT_NAME));
        TextMessage textMessage=null;
        for (int i = 0; i < count; i++) {
            textMessage = session.createTextMessage(MSG+(i+1));
            producer.send(textMessage);
        }

        log.info("【complete】 successfully");

        log.info("【release】 sources.");
        producer.close();
        session.close();
        connection.close();
    }


    private static void producer_asyn_01(int count) throws JMSException {
        connectionFactory.setUseAsyncSend(true);
        connection = connectionFactory.createConnection();
        connection.start(); // 启动访问
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【Sender】 send msg to queue:" + MSG);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(session.createQueue(DEFAULT_NAME));
        TextMessage textMessage=null;
        for (int i = 0; i < count; i++) {
            textMessage = session.createTextMessage(MSG+(i+1));
            textMessage.setJMSCorrelationID(UUID.randomUUID().toString()+"-->orderID");
            String messageID = textMessage.getJMSMessageID();
            producer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("【SUCCESS】"+messageID);
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println("【FAILED】 " +messageID);
                }
            });
        }

        log.info("【complete】 successfully");

        log.info("【release】 sources.");
        producer.close();
        session.close();
        connection.close();
    }
    private static void producer_persistent_01(int count) throws JMSException {
        connection.start(); // 启动访问
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【Sender】 send msg to queue:" + MSG);
        MessageProducer producer = session.createProducer(session.createQueue(DEFAULT_NAME));
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage textMessage=null;
        for (int i = 0; i < count; i++) {
            textMessage = session.createTextMessage(MSG+(i+1));
            producer.send(textMessage);
        }

        log.info("【complete】 successfully");

        log.info("【release】 sources.");
        producer.close();
        session.close();
        connection.close();
    }


    private static void producer_delay_01(int count) throws JMSException {
        connection.start(); // 启动访问
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【Sender】 send msg to queue:" + MSG);
        MessageProducer producer = session.createProducer(session.createQueue(DEFAULT_NAME));
        TextMessage textMessage=null;
        for (int i = 0; i < count; i++) {
            textMessage = session.createTextMessage(MSG+(i+1));
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,3000);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,3000);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,3000);
            producer.send(textMessage);
        }

        log.info("【complete】 successfully");

        log.info("【release】 sources.");
        producer.close();
        session.close();
        connection.close();
    }
}
