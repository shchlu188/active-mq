package com.scl.springbootmqconsumer;

import com.scl.springbootmqconsumer.ma.MQ_Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.stream.IntStream;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description
 **********************************/
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestActiveMQ {
    @Autowired
    private MQ_Consumer consumer;
    @Autowired
    private TextMessage message;


    /**
     * producer
     */
    @Test
    public void testConsumer() throws Exception {

        IntStream.range(0,3).forEach(idx->{
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"【START RECEIVE MESSAGE】.....");
                    System.out.println(Thread.currentThread().getName()+"【RECEIVE】"+message.getText());
                    Thread.sleep(3_000);

                } catch (JMSException | InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(idx)+"--【TOPIC】").start();
        });
       Thread.sleep(Integer.MAX_VALUE);
    }
}
