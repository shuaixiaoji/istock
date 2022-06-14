package com.github.istock;

import com.alibaba.fastjson.JSONArray;
import com.github.istock.enums.InterfacesEnums;
import com.github.istock.utils.Template;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/13 14:54
 */
public class Test {

    public static void main(String[] args) {
        RestTemplate restTemplate = Template.getRestTemplate();
//        String requestUrl1 = "http://157.0.19.2:10120/api/public/js_news";
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUrl1).queryParam("timestamp","2022-04-25 17:57:18");
//        System.out.println(restTemplate.getForObject(builder.build().toString(), Object.class));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Template.getUrl(InterfacesEnums.STOCK_NEW_A_SPOT_EM.getInterfaceUrl()));
        System.out.println(restTemplate.getForObject(builder.build().toString(), JSONArray.class));
    }

}
