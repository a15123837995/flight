package xyz.xfcloud.flight.dubbo.server;

/**
 * 发送邮件接口
 * created by Jizhi on 2019/10/23
 */
public interface EmailServer {

    /**
     * 发送邮件
     * @param addr 邮箱地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return
     */
    boolean sendEmail(String addr,String subject,String content);
}
