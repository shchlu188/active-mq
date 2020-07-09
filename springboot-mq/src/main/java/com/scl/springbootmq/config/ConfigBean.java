package com.scl.springbootmq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Component
@EnableJms
public class ConfigBean {
    @Value("${myQueue}")
    private String myQueue;
    @Value("${myTopic}")
    private String myTopic;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }


    @Bean
    public Topic topic(){
        return new ActiveMQTopic(myTopic);
    }

    /**
     * SpringBoot >>>>>>>>>JmsMessagingTemplate
     * Spring>>>>>>>>>>>>>>JmsTemplate
     * @return
     */
}
