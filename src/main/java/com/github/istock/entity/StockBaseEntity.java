package com.github.istock.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.istock.constants.Constant;
import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 14:49
 */
@Data
@Table(name = "stock_base_info")
public class StockBaseEntity {
    // 编码
    private String code;
    // 名称
    private String name;
    // 当前价格
    private BigDecimal recentPrice;
    // 市盈率
    private BigDecimal per;
    // 市净率
    private BigDecimal pbr;
    // 总市值
    private BigDecimal totalAmount;
    // 流通市值
    private BigDecimal circulationAmount;
    // 60日涨幅
    private BigDecimal gain60;
    // 年初至今涨幅
    private BigDecimal gainYear;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    private static StockBaseEntity convertJsonToBean(JSONObject json) {
        StockBaseEntity resp = new StockBaseEntity();
        resp.setCode(json.getString("代码"));
        resp.setName(json.getString("名称"));
        resp.setRecentPrice(json.getBigDecimal("最新价"));
        resp.setPer(json.getBigDecimal("市盈率-动态"));
        resp.setPbr(json.getBigDecimal("市净率"));
        resp.setTotalAmount(Optional.ofNullable(json.getBigDecimal("总市值")).orElse(Constant.ZERO).divide(Constant.TEN_THOUSAND,3,BigDecimal.ROUND_HALF_UP));
        resp.setGain60(json.getBigDecimal("60日涨跌幅"));
        resp.setGainYear(json.getBigDecimal("年初至今涨跌幅"));
        return resp;
    }

    public static List<StockBaseEntity> convertArrayToBeanList(JSONArray allAStock){
        return allAStock.stream().map(object -> {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
            return convertJsonToBean(json);
        }).collect(Collectors.toList());
    }
}
