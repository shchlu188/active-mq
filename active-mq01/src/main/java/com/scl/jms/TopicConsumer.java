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
public class TopicConsumer {
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
        topic_persistent_base01();

    }

    private static void topic_base01() throws JMSException, InterruptedException {
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建】>>>>>>>>consumer");
        MessageConsumer consumer = session.createConsumer(topic);
        log.info("【设置监听事件】");
        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                try {
                    String tMsg = tm.getText();
                    log.info("【读取数据】>>>>>>>>>>>>>>>>>>" + tMsg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }else {
                log.warn("类型不匹配");
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
        consumer.close();
        session.close();
        connection.close();
    }
    private static void topic_persistent_base01() throws JMSException, InterruptedException {
        connection.setClientID("provider-one");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建】>>>>>>>>TopicSubscriber");
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "remarking...........");
        connection.start();
        Message receive = subscriber.receive();

            while (null!=receive){
                TextMessage textMessage = (TextMessage) receive;
                System.out.println("【接收消息】>>>>>>>>>>>>>"+textMessage.getText());
                receive  = subscriber.receive(5);
            }
        subscriber.close();
        session.close();
        connection.close();
    }

    private static void topic_acknowledgeMode_base01() throws JMSException, InterruptedException {
        connection.start();
        log.info("【acknowledgeMode】 开启签收");
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Topic topic = session.createTopic(DEFAULT_NAME);
        log.info("【创建】>>>>>>>>consumer");
        MessageConsumer consumer = session.createConsumer(topic);
        log.info("【设置监听事件】");
        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                try {
                    String tMsg = tm.getText();
                    log.info("【读取数据】>>>>>>>>>>>>>>>>>>" + tMsg);
                    log.info("手动进行   【签收】 ");
                    tm.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }else {
                log.warn("类型不匹配");
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
        consumer.close();

        session.close();
        connection.close();
    }
}