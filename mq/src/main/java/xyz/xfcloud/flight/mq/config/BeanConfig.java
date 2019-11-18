package xyz.xfcloud.flight.mq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * created by Jizhi on 2019/10/23
 */
@Configuration
public class BeanConfig {

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("ActiveMQQueue");
    }
}
