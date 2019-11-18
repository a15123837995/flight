package xyz.xfcloud.flight.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import xyz.xfcloud.flight.util.http.ConstantUtils;
import xyz.xfcloud.flight.util.http.HttpConnectionFactory;

import java.util.Map;

/**
 * created by Jizhi on 2019/10/12
 */
@Slf4j
public class HttpSendTool {

    public static String sendPostJson(String httpUrl, String requestBody, Map<String,String> headerMap){
        return sendPost(httpUrl,requestBody,headerMap,"json");
    }

    public static String sendPostForm(String httpUrl, String requestBody, Map<String,String> headerMap){
        return sendPost(httpUrl,requestBody,headerMap,"form");
    }

    public static String sendPostText(String httpUrl, String requestBody, Map<String,String> headerMap){
        return sendPost(httpUrl,requestBody,headerMap,"text");
    }

    public static String sendPost(String httpUrl, String requestBody, Map<String,String> headerMap,String contentType){
        HttpEntity entity = null;
        HttpResponse httpResponse = null;
        String result = "";
        HttpClient httpClient = null;
        try{
            // 创建连接
            httpClient = HttpConnectionFactory.getInstance().getHttpClient();
            HttpPost httpPost = HttpConnectionFactory.getInstance().getHttpPost(httpUrl,headerMap,contentType);
            // 设置报文
            StringEntity httpEntity = new StringEntity(requestBody,"UTF-8");
            if ("form".equals(contentType)){
                httpEntity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
            }else if ("text".equals(contentType)){
                httpEntity.setContentType("text/xml;charset=utf-8");
            }else {
                httpEntity.setContentType("application/json");
            }
            //httpEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(httpEntity);

            httpResponse = httpClient.execute(httpPost);
            if (httpResponse != null
                    && httpResponse.getStatusLine().getStatusCode() == ConstantUtils.HTTP_SUCCESS_CODE) {
                // 获取响应的实体
                entity = httpResponse.getEntity();
                // 响应的内容不为空
                if (entity != null) {
                    try {
                        // 请求成功，能获取到响应内容
                        result = EntityUtils.toString(entity);
                        log.debug("响应结果：" + result);
                    } catch (Exception e) {
                        // 记录日志
                        log.error("解析响应报文异常", e);
                        // 获取内容失败，返回空字符串
                        result = "";
                    }
                    finally {
                        EntityUtils.consume(entity);
                    }
                } else {
                    // 请求成功，但是获取不到响应内容
                    result = "";
                }
            }
            else {
                // 非200状态
                // 获取响应的实体
                entity = httpResponse.getEntity();
                // 响应的内容不为空，并且响应的内容长度大于0,则获取响应的内容
                if (entity != null && entity.getContentLength() > 0) {
                    try {
                        // 请求成功，能获取到响应内容
                        result = EntityUtils.toString(entity);
                        log.debug("响应结果：" + result);
                    } catch (Exception e) {
                        // 记录日志
                        log.error("解析响应报文异常", e);
                        // 获取内容失败，返回空字符串
                        result = "";
                    }
                    finally {
                        EntityUtils.consume(entity);
                    }
                }
            }
        } catch (Exception e){
            log.error("发送HTTP请求异常："+ e );
        }
        return result;
    }
}
