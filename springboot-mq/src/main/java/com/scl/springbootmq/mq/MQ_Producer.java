package com.scl.springbootmq.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;
import java.lang.reflect.ParameterizedType;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Component
public class MQ_Producer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"***********boot message send***************");
    }

    /**
     * 间隔时间定投
     * The annotated method must expect no arguments.
     */
    @Scheduled(fixedDelay = 3000l)
    public void produceMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(queue,"***********boot message Scheduled send***************");

        System.out.println("msg send ok");

    }


    @Scheduled(fixedDelay = 3000l)
    public void producesMsgTopicScheduled(){
        jmsMessagingTemplate.convertAndSend(topic,"########SEND############TOPIC PRODUCER#######MESSAGE###########");
        System.out.println("【TOPIC MSG】<<< SEND OK >>>");
    }
}
