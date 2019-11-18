package xyz.xfcloud.flight.dubbo.consumer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/24
 */
@PropertySource(value = "classpath:/sms.properties")
@Component
@Data
public class SmsConfig implements Serializable {

    private static final long serialVersionUID = -5081960064732294448L;

    @Value("${sms.context.template}")
    private String smsContextTemplate;

}
