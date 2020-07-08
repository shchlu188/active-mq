package com.scl.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/6
 * @Description
 **********************************/
@Slf4j
public class TopicProducer {
    private static final String DEFAULT_URL = "tcp://192.168.137.135:61616";
    private static final String DEFAULT_NAME = "Topic";
    private static final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_URL);
    private static Connection connection;

    static {
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        String msg = "topic";
        topic_producer_persistent_base01(msg, 5);
    }

    private static void topic_producer_base01(String msg) throws JMSException {
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【创建topic】");
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建Producer】");
        MessageProducer producer = session.createProducer(topic);
        log.info("【发送数据】>>>>>> START");
        TextMessage textMessage = session.createTextMessage(msg);
        producer.send(textMessage);
        log.info("【发送数据】>>>>>> END");

        log.info("【释放资源】》》》》》》》》》》》》");
        producer.close();
        session.close();
        connection.close();
    }

    private static void topic_producer_persistent_base01(String msg) throws JMSException {


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【创建topic】");
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建Producer】");
        MessageProducer producer = session.createProducer(topic);
        log.info("【Producer】》》》》持久化");
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        log.info("【发送数据】>>>>>> START");
        TextMessage textMessage = session.createTextMessage(msg);
        producer.send(textMessage);
        log.info("【发送数据】>>>>>> END");

        log.info("【释放资源】》》》》》》》》》》》》");
        producer.close();
        session.close();
        connection.close();
    }

    private static void topic_producer_persistent_base01(String msg, int count) throws JMSException {


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        log.info("【创建topic】");
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建Producer】");
        MessageProducer producer = session.createProducer(topic);
        log.info("【Producer】》》》》持久化");
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();

        log.info("【发送数据】>>>>>> START" + count);
        TextMessage textMessage = session.createTextMessage(msg);
        for (int i = 0; i < count; i++) {
            producer.send(textMessage);
            log.info("【数据已发送】: " + (i+1) + "次");
        }
        log.info("【发送数据】>>>>>> END");

        log.info("【释放资源】》》》》》》》》》》》》");
        producer.close();
        session.commit();
        session.close();
        connection.close();
    }
    private static void topic_producer_transaction_base01(String msg) throws JMSException {
        connection.start();
        log.info("<<<<<<<<<<<<<<<【开启事务】>>>>>>>>>>>>>>>>");
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        log.info("【创建topic】");
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建Producer】");
        MessageProducer producer = session.createProducer(topic);
        log.info("<<<<<<<<【发送数据】>>>>>> START");
        TextMessage textMessage = session.createTextMessage(msg);
        producer.send(textMessage);
        log.info("<<<<<<<<【发送数据】>>>>>> END");

        log.info("《《《《《《《《《《【释放资源】》》》》》》》》》》》》");
        producer.close();
        log.info("<<<<<<<<<<<<<<<<<<<<【提交事务】>>>>>>>>>>>>>>>>>>>");
        session.commit();
        session.close();
        connection.close();
    }

}
