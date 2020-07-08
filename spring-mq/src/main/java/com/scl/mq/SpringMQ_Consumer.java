package com.scl.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Service
public class SpringMQ_Consumer {
    @Autowired
    private JmsTemplate jmsQueueTemplate;
    @Autowired
    private JmsTemplate jmsTopicTemplate;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("springContext.xml");
        SpringMQ_Consumer consumer = ac.getBean(SpringMQ_Consumer.class);
        String msg = (String) consumer.jmsTopicTemplate.receiveAndConvert();

        System.out.println("【consumer】>>>>>>>>>>>"+msg);

    }

    private static void consumer_queue(ApplicationContext ac) {
        SpringMQ_Consumer consumer = ac.getBean(SpringMQ_Consumer.class);

        String msg = (String) consumer.jmsQueueTemplate.receiveAndConvert();

        System.out.println("【consumer】>>>>>>>>>>>"+msg);
    }
}
