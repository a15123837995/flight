package xyz.xfcloud.flight.dubbo.service.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/12
 */
@PropertySource(value = "classpath:/tmc.properties")
@Component
@Data
public class TmcConfig implements Serializable {

    private static final long serialVersionUID = -6436505130170432903L;

    @Value("${tmc.url}")
    private String url;

    @Value("${tmc.header.key}")
    private String headerKey;

    @Value("${tmc.header.value}")
    private String headerValue;

}
