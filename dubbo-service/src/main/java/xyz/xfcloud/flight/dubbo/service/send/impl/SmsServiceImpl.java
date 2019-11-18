package xyz.xfcloud.flight.dubbo.service.send.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import sun.misc.BASE64Encoder;
import xyz.xfcloud.flight.dubbo.server.SmsServer;
import xyz.xfcloud.flight.dubbo.service.config.EmailConfig;
import xyz.xfcloud.flight.dubbo.service.config.SmsConfig;
import xyz.xfcloud.flight.util.HttpSendTool;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 发送短信
 * created by Jizhi on 2019/10/23
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsServer {

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private EmailConfig emailConfig;

    @Override
    public boolean sendSMS(String phone, String content) {
        try{
            if (phoneInRedis(phone)){
                //System.out.println("已发送过短信，尚未过期");
                return false;
            }
            if (send(phone,content)){
                // 30分钟有效期
                ttl(phone,60*30);
            }
        } catch (Exception e){
            log.error("发送短信失败,{}"+ e);
            return false;
        }
        return true;
    }


    /**
     * 发送短信
     * @param phone 手机号码
     * @param content 短信内容
     * @return
     * @throws Exception
     */
    private boolean send(String phone, String content) throws Exception{
        String secretId = smsConfig.getSecretId();
        String secretKey = smsConfig.getSecretKey();
        String source = smsConfig.getSource();

        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        // 认证签名
        String auth = calcAuthorization(source, secretId, secretKey, datetime);

        // 请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Source", source);
        headers.put("X-Date", datetime);
        headers.put("Authorization", auth);

        // 请求体
        Map<String, String> bodyParams = new HashMap<String, String>();
        bodyParams.put("account", smsConfig.getAccount());
        bodyParams.put("content", smsConfig.getSign()+content);//短信内容，需要加上短信签名
        bodyParams.put("extno", smsConfig.getExtno());
        bodyParams.put("mobile", phone);
        bodyParams.put("password", smsConfig.getPassword());
        bodyParams.put("rt", smsConfig.getRt());

        String url = smsConfig.getUrl();
        String request = urlEncode(bodyParams);

        log.info("向{}发送请求参数{}",url,request);
        String response = HttpSendTool.sendPostForm(url,request,headers);
        log.info("短信API返回结果：" + response);

        String status = null;
        Integer result = -1;
        JSONObject json = JSONObject.parseObject(response);
        if (json != null){
            // status=0，提交成功
            status = json.getString("status");
            // list->result=0，发送成功
            JSONArray jsonArray = json.getJSONArray("list");
            if (jsonArray != null && jsonArray.size() > 0){
                JSONObject jo = jsonArray.getJSONObject(0);
                result = jo.getInteger("result");
            }
        }

        if ("0".equals(status) && result == 0){
            // 发送短信成功
            Integer balance = json.getInteger("balance");
            if (balance != null && balance < 350){
                log.warn("短信余额不足！");
                // 只能发送10条短信了，发送邮件，进行告警
                emailService.sendEmail(emailConfig.getAddr(),emailConfig.getSubject(),emailConfig.getContext());
            }
            return true;
        }else {
            log.warn("短信发送失败，响应报文：" + response);
            if (result == 15){
                // 余额不足，发送邮件通知
                emailService.sendEmail(emailConfig.getAddr(),emailConfig.getSubject(),emailConfig.getContext());
            }
            // 短信发送失败
            return false;
        }
    }

    /**
     * 认证
     */
    private String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = new BASE64Encoder().encode(hash);

        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    /**
     * 编码
     */
    private String urlEncode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }

    /**
     * 查看手机号是否存在于Redis
     * @param phone 手机号
     * @return
     */
    private boolean phoneInRedis(String phone){
        Long expire = redisTemplate.getExpire(phone);
        if (expire >0 ){
            return true;
        }
        return false;
    }

    /**
     * 设置过期时间
     * @param phone 手机号码
     * @param timeout 单位（秒）
     */
    private void ttl(String phone,long timeout){
        redisTemplate.opsForValue().set(phone,"phone",timeout, TimeUnit.SECONDS);
    }

}
