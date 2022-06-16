package com.github.istock.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.istock.enums.MarketInterfacesEnums;
import com.github.istock.utils.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shuaixiaoji
 * @Description 千股千评相关
 * @date 2022/6/16 16:48
 */
public class StockCommentService {

    public static void main(String[] args) {
        Map<String,String> params = new HashMap<>();

        params.put("symbol","002274");

        // 机构参与度
//        JSONArray resultArray = Template.requestForJsonArray(MarketInterfacesEnums.STOCK_COMMENT_DETAIL_ZLKP_JGCYD_EM.getInterfaceUrl(),
//                params);
//        System.out.println(JSON.toJSONString(resultArray));

        // 历史评分
        JSONArray hisScore = Template.requestForJsonArray(MarketInterfacesEnums.STOCK_COMMENT_DETAIL_ZHPJ_LSPF_EM.getInterfaceUrl(),
                params);

        // 用户关注度
        JSONArray userFocus = Template.requestForJsonArray(MarketInterfacesEnums.STOCK_COMMENT_DETAIL_SCRD_FOCUS_EM.getInterfaceUrl(),
                params);

        // 市场参与意愿
        JSONArray userDesire = Template.requestForJsonArray(MarketInterfacesEnums.STOCK_COMMENT_DETAIL_SCRD_DESIRE_EM.getInterfaceUrl(),
                params);

        // 日度参与意愿
        JSONArray userDesireDaily = Template.requestForJsonArray(MarketInterfacesEnums.STOCK_COMMENT_DETAIL_SCRD_DESIRE_DAILY_EM.getInterfaceUrl(),
                params);

        // 市场成本
        JSONArray costArray = Template.requestForJsonArray(MarketInterfacesEnums.STOCK_COMMENT_DETAIL_SCRD_COST_EM.getInterfaceUrl(),
                params);

        System.out.println(JSON.toJSONString(hisScore));
        System.out.println(JSON.toJSONString(userFocus));
        System.out.println(JSON.toJSONString(userDesire));
        System.out.println(JSON.toJSONString(userDesireDaily));
        System.out.println(JSON.toJSONString(costArray));

    }
}
