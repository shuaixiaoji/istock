package com.github.istock.enums;

import com.github.istock.entity.AnalystRankEntity;
import com.github.istock.entity.InnerTradeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/14 9:28
 */
@Getter
@AllArgsConstructor
public enum InterfacesEnums {
    /**
     * 接口code对应描述，返回报文格式
     */
    STOCK_SSE_SUMMARY("数据总貌","stock_sse_summary", String.class),
    STOCK_NEW_A_SPOT_EM("新股实时","stock_new_a_spot_em", String.class),
    STOCK_ZH_A_STOP_EM("退市查询","stock_zh_a_stop_em", String.class),
    STOCK_INNER_TRADE_XQ("内部交易", "stock_inner_trade_xq", InnerTradeEntity.class),
    STOCK_ANALYST_RANK_EM("分析师指数", "stock_analyst_rank_em", AnalystRankEntity.class),
    
    ;
    private String desc;
    private String interfaceUrl;
    private Class<?> clas;

    InterfacesEnums(String desc, String interfaceUrl) {
        this.desc = desc;
        this.interfaceUrl = interfaceUrl;
    }

    public static InterfacesEnums getEnumByUrl(String url) {
        Map<Object, InterfacesEnums> enumMap = Arrays.stream(InterfacesEnums.values())
            .collect(Collectors.toMap(InterfacesEnums::getInterfaceUrl, Function.identity()));
        return enumMap.getOrDefault(url, null);
    }

    public static Map<String, InterfacesEnums> getInterfaceMap() {
        return Arrays.stream(InterfacesEnums.values())
            .collect(Collectors.toMap(InterfacesEnums::getInterfaceUrl, Function.identity()));
    }
}
