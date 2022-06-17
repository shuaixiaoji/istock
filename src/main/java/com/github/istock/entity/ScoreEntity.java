package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

/**
 * @author shuaixiaoji
 * @Description 评分 & 关注度
 * @date 2022/6/16 22:47
 */
@Data
public class ScoreEntity {

    @ExcelProperty("代码")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("日期")
    private Date date;

    @ExcelProperty("评分")
    private String socre;

    @ExcelProperty("用户关注度")
    private String focus;

    @ExcelProperty("收盘价")
    private String price;

    public static ScoreEntity convertJsonToBean(JSONObject json){
        ScoreEntity scoreEntity  = new ScoreEntity();
        scoreEntity.setDate(json.getDate("日期"));
        scoreEntity.setFocus(json.getString("用户关注度指数"));
        scoreEntity.setSocre(json.getString("评分"));
        scoreEntity.setPrice(json.getString("收盘价"));
        // TODO 动态获取ß
        scoreEntity.setCode("002274");
        scoreEntity.setName("华昌化工");
        return scoreEntity;
    }
}
