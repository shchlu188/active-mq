package com.scl.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/6
 * @Description
 **********************************/
public class MQUtils {
    private static final String DEFAULT_URL = "tcp://192.168.137.133:61616";
    private static Connection connection = null;
    public static Session getSession() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_URL);

        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return session;
    }

    public static void releaseResources(){
        if (connection!=null){
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
