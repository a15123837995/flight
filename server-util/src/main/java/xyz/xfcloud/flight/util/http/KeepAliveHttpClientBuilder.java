package xyz.xfcloud.flight.util.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * created by Jizhi on 2019/10/12
 */
public class KeepAliveHttpClientBuilder implements HttpClientBuilder {
    @Override
    public HttpClient getHttpClient() {
        return HttpConnectionManager.getInstance().getHttpClient();
    }

    private static class HttpConnectionManager {

        private static HttpClient httpClient;

        private static class ConnectorHolder {

            private static final HttpConnectionManager INSTANCE = new HttpConnectionManager();
        }

        public static HttpConnectionManager getInstance () {
            return ConnectorHolder.INSTANCE;
        }

        public synchronized HttpClient getHttpClient () {
            if (httpClient == null) {

                LayeredConnectionSocketFactory sslsf = null;
                try {
                    sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                        .<ConnectionSocketFactory> create().register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory()).build();
                PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                        socketFactoryRegistry);
                cm.setMaxTotal(5 * ConstantUtils.POOL_NUMBER);
                cm.setDefaultMaxPerRoute(ConstantUtils.POOL_NUMBER);

                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(ConstantUtils.HTTP_REQUEST_TIMEOUT)
                        // .setConnectionRequestTimeout(1000)
                        .setSocketTimeout(ConstantUtils.HTTP_RESPONSE_TIMEOUT).build();
                // 创建连接
                httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                        .setConnectionManager(cm).build();
            }

            return httpClient;
        }
    }
}
