package com.scl.mq;

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
    private static final String DEFAULT_URL = "tcp://192.168.137.133:61616";
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

}
