package com.scl.springbootmq;

import com.scl.springbootmq.mq.MQ_Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
    private MQ_Producer producer;


    /**
     * producer
     */
    @Test
    public void testProducer() {
        producer.produceMsg();
    }
}
