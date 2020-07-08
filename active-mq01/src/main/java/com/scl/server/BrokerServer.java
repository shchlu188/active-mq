package com.scl.server;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerView;

/***********************************
 * @QQ 1578380573
 * @author scl
 * @Date 2020/7/8
 * @Description 内嵌式的服务实列
 **********************************/
public class BrokerServer {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
