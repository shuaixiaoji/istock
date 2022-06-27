package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * AnalysisHoldRankEntity
 *
 * @author mac-huanglc
 * @since 2022/6/27
 */
@Data
public class AnalysisHoldRankEntity {

    @ExcelProperty("股票代码")
    private String stockCode;

    @ExcelProperty("股票持有分析师人数")
    private long num;

    /**
     * 数据转换，必填
     *
     * @param obj obj
     * @return obj
     */
    public static AnalysisHoldRankEntity convertJsonToBean(Object obj) {
        JSONObject object = JSONObject.parseObject(JSON.toJSONString(obj));
        AnalysisHoldRankEntity resp = new AnalysisHoldRankEntity();
        resp.setStockCode(object.getString("股票代码"));
        resp.setNum(object.getIntValue("股票持有分析师人数"));
        return resp;
    }
}
