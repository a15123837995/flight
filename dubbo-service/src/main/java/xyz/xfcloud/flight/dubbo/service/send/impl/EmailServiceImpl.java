package xyz.xfcloud.flight.dubbo.service.send.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import xyz.xfcloud.flight.dubbo.server.EmailServer;
import xyz.xfcloud.flight.dubbo.service.config.EmailConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 发送电子邮件
 * created by Jizhi on 2019/10/23
 */
@Service
public class EmailServiceImpl implements EmailServer {

    @Autowired
    private EmailConfig config;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 发送邮件
     * @param addr 邮箱地址
     * @param subject 邮件主题
     * @param content 邮件正文
     * @return
     */
    @Override
    public boolean sendEmail(String addr, String subject, String content) {
        try {
            if (emailInRedis(addr)){
                //System.out.println("已发送过邮件，尚未过期");
                return false;
            }
            // 30分钟有效期
            ttl(addr,60*30);

            Properties props = new Properties();
            // 表示SMTP发送邮件，必须进行身份验证
            props.put("mail.smtp.auth", config.getAuth());
            // 此处填写SMTP服务器
            props.put("mail.smtp.host", config.getHost());
            // 端口号
            props.put("mail.smtp.port", config.getPort());
            // 此处填写自己的账号
            props.put("mail.user", config.getUser());
            // 此处的密码为腾讯生成的授权码，16位STMP口令
            props.put("mail.password", config.getPassword());

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(addr);
            message.setRecipient(Message.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(subject);

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查看email是否存在于Redis
     * @param email 邮箱地址
     * @return
     */
    private boolean emailInRedis(String email){
        Long expire = redisTemplate.getExpire(email);
        if (expire >0 ){
            return true;
        }
        return false;
    }

    /**
     * 设置过期时间
     * @param email 邮箱地址
     * @param timeout 单位（秒）
     */
    private void ttl(String email,long timeout){
        redisTemplate.opsForValue().set(email,"email",timeout, TimeUnit.SECONDS);
    }
}
