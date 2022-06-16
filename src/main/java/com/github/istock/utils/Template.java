package com.github.istock.utils;

import com.alibaba.fastjson.JSONArray;
import com.github.istock.intercepter.RestTemplateInterceptor;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shuaixiaoji
 * @Description 模板方法
 * @date 2022/6/13 14:43
 */
public class Template {
    private static final String URL_HEAD = "http://157.0.19.2:10120/api/public/";

    /**
     * 获取请求模板
     *
     * @return
     */
    private static RestTemplate getRestTemplate() {
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

    private static String getUrl(String infUrl) {
        return URL_HEAD + infUrl;
    }

    /**
     * get方法获取JSONArray结果
     *
     * @param interfaceUrl 接口名称
     * @param params  <k,v>参数
     * @return
     */
    public static JSONArray requestForJsonArray(String interfaceUrl, Map<String, String> params) {
        RestTemplate restTemplate = getRestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Template.getUrl(interfaceUrl));
        if (MapUtils.isEmpty(params)) {
            return restTemplate.getForObject(builder.build().toString(), JSONArray.class);
        }
        params.forEach((key, value) -> {
            builder.queryParam(key, value);
        });
        return restTemplate.getForObject(builder.build().toString(), JSONArray.class);
    }
}
