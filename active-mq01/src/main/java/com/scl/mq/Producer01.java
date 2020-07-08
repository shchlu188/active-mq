package com.scl.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.graalvm.compiler.nodes.calc.PointerEqualsNode;

import javax.jms.*;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/6
 * @Description
 **********************************/
@Slf4j
public class Producer01 {
    private static final String DEFAULT_URL = "tcp://192.168.137.133:61616";
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

        producer_01(6);
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
}
