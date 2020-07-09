package com.scl.springbootmqconsumer.config;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Component
@EnableJms
public class ConfigBean {

    @Bean
    public TextMessage textMessage(){
        return new ActiveMQTextMessage();
    }

}
