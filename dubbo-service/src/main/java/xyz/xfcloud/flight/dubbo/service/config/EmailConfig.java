package xyz.xfcloud.flight.dubbo.service.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/23
 */
@PropertySource(value = "classpath:/email.properties")
@Component
@Data
public class EmailConfig implements Serializable {

    private static final long serialVersionUID = 4252844811107687587L;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;

    @Value("${mail.user}")
    private String user;

    @Value("${mail.password}")
    private String password;

    @Value("${warn.mail.addr}")
    private String addr;

    @Value("${warn.mail.subject}")
    private String subject;

    @Value("${warn.mail.context}")
    private String context;

}
