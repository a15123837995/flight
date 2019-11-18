package xyz.xfcloud.flight.dubbo.service.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * created by Jizhi on 2019/10/12
 */
@PropertySource(value = "classpath:/sms.properties")
@Component
@Data
public class SmsConfig implements Serializable {

    private static final long serialVersionUID = -1492593914751926841L;

    /**
     * 云市场分配的密钥Id
     */
    @Value("${tencent.cloud.secretId}")
    private String secretId;

    /**
     * 云市场分配的密钥Key
     */
    @Value("${tencent.cloud.secretKey}")
    private String secretKey;

    /**
     * 来源：云市场
     */
    @Value("${tencent.cloud.source}")
    private String source;

    /**
     * 发送短信的请求地址
     */
    @Value("${tencent.cloud.url}")
    private String url;

    /**
     * 发送账号
     */
    @Value("${tencent.cloud.three.account}")
    private String account;

    /**
     * 通道号
     */
    @Value("${tencent.cloud.three.extno}")
    private String extno;

    /**
     * 发送密码
     */
    @Value("${tencent.cloud.three.password}")
    private String password;

    /**
     * 发送格式
     */
    @Value("${tencent.cloud.three.rt}")
    private String rt;

    /**
     * 签名
     */
    @Value("${xyz.xfcloud.sms.sign}")
    private String sign;
}
