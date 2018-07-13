package com.flyingwillow.springbootproptype.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClientConfig {


    private int connectionTimeToLive = 20;
    private int maxTotalConnection = 50;
    private int maxPerRoute = 20;
    private int socketTimeout = 20000;
    private int connectionTimeout = 20000;
    private int connectionRequestTimeout = 20000;

    @Bean
    public HttpClientBuilder httpClientBuilder(){
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(connectionTimeToLive, TimeUnit.SECONDS);
        poolingConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
        poolingConnectionManager.setMaxTotal(maxTotalConnection);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();

        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        BasicCookieStore cookieStore = new BasicCookieStore();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(poolingConnectionManager)
                .setDefaultRequestConfig(requestConfig).setRedirectStrategy(redirectStrategy)
                .setDefaultCookieStore(cookieStore);
        return httpClientBuilder;
    }

    @Bean
    public CloseableHttpClient getHttpClient(){

        return httpClientBuilder().build();

    }


}
