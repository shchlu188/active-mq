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
public class TopicConsumer {
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
        topic_base01();

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


}