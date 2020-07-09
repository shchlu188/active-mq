package com.scl.springbootmqconsumer.ma;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Component
public class MQ_Consumer {
    @JmsListener(destination = "${myQueue}")
    public void receiveMsg(TextMessage message) throws JMSException {
        System.out.println("【CONSUMER】>>>RECEIVE MESSAGE>>> "+message.getText());
    }

    @JmsListener(destination = "${myTopic}")
    public void receiveMsgFromToTopic(TextMessage message) throws JMSException {
        System.out.println(Thread.currentThread().getName()+ "【CONSUMER】>>>TOPIC RECEIVE MESSAGE>>> "+message.getText());
    }


}
