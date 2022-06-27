package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * AnalystDetailEntity
 *
 * @author mac-huanglc
 * @since 2022/6/21
 */
@Data
public class AnalystDetailEntity {

    @ExcelProperty("分析师id")
    private String id;

    @ExcelProperty("分析师名称")
    private String name;
    
    @ExcelProperty("序号")
    private String index;

    @ExcelProperty("股票代码")
    private String stockCode;

    @ExcelProperty("股票名称")
    private String stockName;

    @ExcelProperty("调入日期")
    private String buyDate;

    @ExcelProperty("最新评级日期")
    private String survey;

    @ExcelProperty("当前评级名称")
    private String currentCode;

    @ExcelProperty("成交价格(前复权)")
    private String buyPrice;

    @ExcelProperty("最新价格")
    private String currentPrice;

    @ExcelProperty("阶段涨跌幅")
    private String gain;

    /**
     * 数据转换，必填
     *
     * @param obj obj
     * @return obj
     */
    public static AnalystDetailEntity convertJsonToBean(Object obj) {
        JSONObject object = JSONObject.parseObject(JSON.toJSONString(obj));
        AnalystDetailEntity resp = new AnalystDetailEntity();
        resp.setBuyPrice(object.getString("成交价格(前复权)"));
        resp.setIndex(object.getString("序号"));
        resp.setStockName(object.getString("股票名称"));
        resp.setStockCode(object.getString("股票代码"));
        resp.setBuyDate(object.getString("调入日期"));
        resp.setGain(object.getString("阶段涨跌幅"));
        resp.setSurvey(object.getString("最新评级日期"));
        resp.setCurrentPrice(object.getString("最新价格"));
        resp.setCurrentCode(object.getString("当前评级名称"));
        return resp;
    }
}
