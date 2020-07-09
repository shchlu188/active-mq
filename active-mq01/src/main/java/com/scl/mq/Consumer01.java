package com.scl.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Comparator;
import java.util.stream.IntStream;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/6
 * @Description
 **********************************/
public class Consumer01 {
    private static final String DEFAULT_URL = "tcp://192.168.137.135:61616";
    private static final String DEFAULT_NAME = "QUEUE";
    private static final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_URL);
    private static Connection connection;

    static {
        try {
            connection = connectionFactory.createConnection();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        IntStream.range(0,2)
                .forEach(idx->{
                    new Thread(()->{
                        try {
                            consumer_thread_01(Thread.currentThread());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },String.valueOf(idx+1+'A')).start();
                });
    }

    /**
     * 第二种 使用监听
     * @throws JMSException
     * @throws InterruptedException
     */

    private static void consumer_asyn_unblock_02() throws JMSException, InterruptedException {
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(DEFAULT_NAME);
        MessageConsumer consumer = session.createConsumer(queue);

        System.out.println("准备接收数据");
        // 相当于开启一个新的线程执行，不注册主业务逻辑。
        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage tx = (TextMessage) message;
                try {
                    System.out.println("【数据】:::::" + tx.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println("接收数据完成");
            } else {
                System.out.println("类型不匹配.....");
            }
            System.out.println("------------replay--------------");
        });
        Thread.sleep(Integer.MAX_VALUE);
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * 第一种
     *
     * @throws JMSException
     */
    private static void consumer_syn_block_01() throws Exception {
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(DEFAULT_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        try {
            while (true) {
                System.out.println("准备接收数据");
                Message receive = consumer.receive(); // 阻塞
                if (receive instanceof TextMessage) {
                    TextMessage tx = (TextMessage) receive;
                    System.out.println("【数据】:::::" + tx.getText());
                    System.out.println("接收数据完成");
                } else {
                    try {
                        throw new ClassNotFoundException("未找到匹配类型");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (JMSException e) {
            System.out.println("【释放资源】");
            consumer.close();
            session.close();
            connection.close();
        }


    }

    /**
     * 1、consumer 2、producer
             * 多线程情况下，平均分配数据
     * 1、producer 2、consumer
             *  随机分配
     * @param thread
     * @throws Exception
     */
    private static void consumer_thread_01(Thread thread) throws Exception {
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(DEFAULT_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        try {
            while (true) {
                System.out.println("【"+thread.getName()+"】"+" 准备接收数据");
                Message receive = consumer.receive(); // 阻塞
                if (receive instanceof TextMessage) {
                    TextMessage tx = (TextMessage) receive;
                    System.out.println("【"+thread.getName()+"】"+"【接收数据】:::::" + tx.getText());
                    System.out.println("【"+thread.getName()+"】"+"接收数据完成");
                } else {
                    try {
                        throw new ClassNotFoundException("【"+thread.getName()+"】"+"未找到匹配类型");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (JMSException e) {
            System.out.println("【"+thread.getName()+"】"+"【释放资源】");
            consumer.close();
            session.close();
            connection.close();
        }


    }

}
