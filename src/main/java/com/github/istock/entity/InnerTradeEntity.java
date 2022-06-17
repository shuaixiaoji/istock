package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * InnerTradeEntity
 *
 * @author mac-huanglc
 * @since 2022/6/17
 */
@Data
public class InnerTradeEntity {

    @ExcelProperty("股票代码")
    private String code;

    @ExcelProperty("股票名称")
    private String name;

    @ExcelProperty("变动日期")
    private String changeTime;

    @ExcelProperty("变动人")
    private String changePerson;

    @ExcelProperty("变动股数")
    private String changeNum;

    @ExcelProperty("成交均价")
    private String price;

    @ExcelProperty("变动后持股数")
    private String holdNum;

    @ExcelProperty("与董监高关系")
    private String relation;

    @ExcelProperty("董监高职务")
    private String job;

    /**
     * 数据转换，必填
     *
     * @param obj obj
     * @return obj
     */
    private static InnerTradeEntity convertJsonToBean(Object obj) {
        JSONObject object = JSONObject.parseObject(JSON.toJSONString(obj));
        InnerTradeEntity resp = new InnerTradeEntity();
        resp.setPrice(object.getString("成交均价"));
        resp.setCode(object.getString("股票代码"));
        resp.setName(object.getString("股票名称"));
        resp.setChangeTime(object.getString("变动日期"));
        resp.setRelation(object.getString("与董监高关系"));
        resp.setChangePerson(object.getString("变动人"));
        resp.setJob(object.getString("董监高职务"));
        resp.setHoldNum(object.getString("变动后持股数"));
        resp.setChangeNum(object.getString("变动股数"));
        return resp;
    }
}
