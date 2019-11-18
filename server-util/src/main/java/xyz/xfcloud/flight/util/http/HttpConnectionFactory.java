package xyz.xfcloud.flight.util.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.Map;

/**
 * created by Jizhi on 2019/10/12
 */
public class HttpConnectionFactory {

    private static HttpConnectionFactory instance = null;

    public static synchronized HttpConnectionFactory getInstance () {
        if (instance == null) {
            instance = new HttpConnectionFactory();
        }
        return instance;
    }

    public synchronized HttpClient getHttpClient () {
        HttpClient httpClient = null;

        HttpClientBuilder httpClientBuilder = new KeepAliveHttpClientBuilder();
        httpClient = httpClientBuilder.getHttpClient();

        return httpClient;
    }

    /**
     * 设置请求地址和请求头
     * @param httpUrl
     * @param map
     * @return
     */
    public HttpPost getHttpPost(String httpUrl , Map<String,String> map,String contentType){
        HttpPost httpPost = null;
        httpPost = new HttpPost(httpUrl);
        if ("form".equals(contentType)){
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        }
        else if ("text".equals(contentType)){
            httpPost.setHeader("Content-Type","text/xml;charset=utf-8");
        }
        else {
            httpPost.setHeader("Content-Type","application/json");
        }
        if (map != null && map.size()>0){
            for (String key : map.keySet()){
                httpPost.setHeader(key,map.get(key));
            }
        }
        return httpPost;
    }
}
