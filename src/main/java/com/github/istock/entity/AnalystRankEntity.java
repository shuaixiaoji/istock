package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * AnalystRankEntity
 *
 * @author mac-huanglc
 * @since 2022/6/17
 */
@Data
public class AnalystRankEntity {

    @ExcelProperty("序号")
    private String index;

    @ExcelProperty("分析师ID")
    private String no;

    @ExcelProperty("分析师名称")
    private String name;

    @ExcelProperty("成分股个数")
    private String num;

    @ExcelProperty("2022最新个股评级")
    private String stock;

    @ExcelProperty("年度指数")
    private String year;

    @ExcelProperty("分析师单位")
    private String company;

    @ExcelProperty("3个月收益率")
    private String three;

    @ExcelProperty("6个月收益率")
    private String six;

    @ExcelProperty("12个月收益率")
    private String twelve;

    @ExcelProperty("2022年收益率")
    private String yearEarnRate;

    /**
     * 数据转换，必填
     *
     * @param obj obj
     * @return obj
     */
    private static AnalystRankEntity convertJsonToBean(Object obj) {
        JSONObject object = JSONObject.parseObject(JSON.toJSONString(obj));
        AnalystRankEntity resp = new AnalystRankEntity();
        resp.setCompany(object.getString("分析师单位"));
        resp.setIndex(object.getString("序号"));
        resp.setName(object.getString("分析师名称"));
        resp.setNo(object.getString("分析师ID"));
        resp.setNum(object.getString("成分股个数"));
        resp.setSix(object.getString("6个月收益率"));
        resp.setStock(object.getString("2022最新个股评级"));
        resp.setTwelve(object.getString("12个月收益率"));
        resp.setThree(object.getString("3个月收益率"));
        resp.setYearEarnRate(object.getString("2022年收益率"));
        resp.setYear(object.getString("年度指数"));
        return resp;
    }
}
