package com.scl.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Service
public class SpringMQ_Producer {
    @Autowired
    private JmsTemplate jmsQueueTemplate;
    @Autowired
    private JmsTemplate jmsTopicTemplate;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("springContext.xml");
        String msg = "Topic msg";
        SpringMQ_Producer producer = ac.getBean(SpringMQ_Producer.class);
        producer_topic(msg, producer);
    }

    private static void producer_topic(String msg, SpringMQ_Producer producer) {
        producer.jmsTopicTemplate.send(session -> {
            TextMessage textMessage = session.createTextMessage(msg);
            return textMessage;

        });
        System.out.println("【Topic】>>>>>>>>>>>>>>>>>>>SUCCESSFUL");
    }

    private static void producer_queue(SpringMQ_Producer producer,String msg) {


        producer.jmsQueueTemplate.send((session)->{
            TextMessage textMessage = session.createTextMessage(msg);
            return textMessage;
        });
        System.out.println("【PRODUCER】》》》》》》》》》》》SEND SUCCESSFUL");
    }
}
