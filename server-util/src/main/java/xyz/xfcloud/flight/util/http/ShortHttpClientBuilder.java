package xyz.xfcloud.flight.util.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;

/**
 * created by Jizhi on 2019/10/12
 */
public class ShortHttpClientBuilder implements HttpClientBuilder {
    @Override
    public HttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(ConstantUtils.HTTP_RESPONSE_TIMEOUT).build();

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();

        return httpClient;
    }
}
