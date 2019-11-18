package xyz.xfcloud.flight.mq.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * created by Jizhi on 2019/10/23
 */
@Service
public class ProviderMQ {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(String message){
        String queue = "addFlight";
        jmsMessagingTemplate.convertAndSend(queue,message);
    }
}
