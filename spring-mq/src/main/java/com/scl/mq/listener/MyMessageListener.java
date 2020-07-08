package com.scl.mq.listener;

import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@Service("queueMessageListener")
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            System.out.println("【MESSAGE-LISTENER】>>>START UP");
            TextMessage tm = (TextMessage) message;
            try {
                System.out.println("【CONTENT】>>>>>"+((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
