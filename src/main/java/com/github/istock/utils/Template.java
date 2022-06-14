package com.github.istock.utils;

import com.github.istock.intercepter.RestTemplateInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shuaixiaoji
 * @Description 模板方法
 * @date 2022/6/13 14:43
 */
public class Template {
    private static final String URL_HEADD = "http://157.0.19.2:10120/api/public/";

    public static RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        // disableCookieManagement() 禁用cookie会话保持功能
        CloseableHttpClient httpClient =
                HttpClientBuilder.create().disableCookieManagement().useSystemProperties().build();
        httpRequestFactory.setHttpClient(httpClient);
        // 设置请求超时时间60s
        httpRequestFactory.setConnectionRequestTimeout(60 * 1000);
        httpRequestFactory.setConnectTimeout(60 * 1000);
        httpRequestFactory.setReadTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        // 添加拦截器
        interceptors.add(new RestTemplateInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    public static String getUrl(String infUrl) {
        return URL_HEADD + infUrl;
    }
}
