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
@Table(name = "stock_his_info")
public class StockHisEntity {
    // 编码
    private String code;
    // 名称
    private String name;
    // 统计数据日期
    private Date staticDate;
    // 统计维度
    private String staticPeriod;
    // 开盘价格
    private BigDecimal openPrice;
    // 收盘
    private BigDecimal closePrice;
    // 最高价
    private BigDecimal maxPrice;
    // 最低价
    private BigDecimal minPrice;
    // 成交量
    private BigDecimal dealNum;
    // 成交额
    private BigDecimal dealAmount;
    // 涨幅
    private BigDecimal upRate;
    // 换手率
    private BigDecimal turnoverRate;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    private static StockHisEntity convertJsonToBean(JSONObject json, String code, String name, String period) {
        StockHisEntity resp = new StockHisEntity();
        resp.setCode(code);
        resp.setName(name);
        resp.setStaticPeriod(period);
        resp.setStaticDate(json.getDate("日期"));
        resp.setOpenPrice(json.getBigDecimal("开盘"));
        resp.setClosePrice(json.getBigDecimal("收盘"));
        resp.setMaxPrice(json.getBigDecimal("最高"));
        resp.setMinPrice(json.getBigDecimal("最低"));
        resp.setDealNum(json.getBigDecimal("成交量"));
        resp.setDealAmount(Optional.ofNullable(json.getBigDecimal("成交额")).orElse(Constant.ZERO).divide(Constant.TEN_THOUSAND,3,BigDecimal.ROUND_HALF_UP));
        resp.setUpRate(json.getBigDecimal("涨跌幅"));
        resp.setTurnoverRate(json.getBigDecimal("换手率"));
        resp.setUpdateTime(new Date());
        resp.setCreateTime(new Date());
        return resp;
    }

    public static List<StockHisEntity> convertArrayToBeanList(JSONArray allAStock, String code, String name,
                                                              String period){
        return allAStock.stream().map(object -> {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
            return convertJsonToBean(json,code,name,period);
        }).collect(Collectors.toList());
    }
}
