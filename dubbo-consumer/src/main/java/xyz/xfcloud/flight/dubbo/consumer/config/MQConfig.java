package xyz.xfcloud.flight.dubbo.consumer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/24
 */
@PropertySource(value = "classpath:/mq.properties")
@Component
@Data
public class MQConfig implements Serializable {

    private static final long serialVersionUID = 1022108935808889423L;

    @Value("${activemq.queue.flight.addTask}")
    private String addFlight;

    @Value("${activemq.queue.sendTo.flight.addTask}")
    private String flightSendTo;

}
