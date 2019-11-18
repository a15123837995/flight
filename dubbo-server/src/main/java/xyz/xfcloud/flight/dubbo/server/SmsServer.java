package xyz.xfcloud.flight.dubbo.server;

/**
 * 发送短信接口
 * created by Jizhi on 2019/10/23
 */
public interface SmsServer {

    /**
     * 发送短信
     * @param phone 电话号码
     * @param content 短信内容
     * @return
     */
    boolean sendSMS(String phone,String content);
}
