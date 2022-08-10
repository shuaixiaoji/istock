package com.github.istock.entity;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class StockNewsEntity {
    // 编码
    @ExcelProperty("编码")
    private String code;

    // 名称
    @ExcelProperty("名称")
    private String name;

    // 统计数据日期
    @ExcelProperty("统计数据日期")
    private String staticDate;

    @ExcelProperty("地址")
    private String url;

    private static StockNewsEntity convertJsonToBean(JSONObject json, String code, String name) {
        StockNewsEntity resp = new StockNewsEntity();
        resp.setCode(code);
        resp.setName(name);
        resp.setStaticDate(DateUtil.format(new Date(), "yyyyMMdd"));
        resp.setUrl(json.getString("url"));
        return resp;
    }

    public static List<StockNewsEntity> convertArrayToBeanList(JSONArray allAStock, String code, String name) {
        return allAStock.stream().map(object -> {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
            return convertJsonToBean(json, code, name);
        }).collect(Collectors.toList());
    }
}
