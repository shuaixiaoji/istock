package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.istock.utils.ExcelUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private String date;

    @ExcelProperty("评分")
    private String socre;

    @ExcelProperty("用户关注度")
    private String focus;

    @ExcelProperty("收盘价")
    private String price;

    private static ScoreEntity convertJsonToBean(JSONObject json){
        ScoreEntity scoreEntity  = new ScoreEntity();
        scoreEntity.setDate(json.getString("日期"));
        scoreEntity.setFocus(json.getString("用户关注指数"));
        scoreEntity.setSocre(json.getString("评分"));
        scoreEntity.setPrice(json.getString("收盘价"));
        // TODO 动态获取名称
        scoreEntity.setCode("002274");
        scoreEntity.setName("华昌化工");
        return scoreEntity;
    }

    public static void exportExcel(JSONArray data,String filePath){
        List<ScoreEntity> scoreEntities = data.stream().map(object -> {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
            return ScoreEntity.convertJsonToBean(json);
        }).collect(Collectors.toList());
        ExcelUtils.simpleWrite(filePath, scoreEntities, ScoreEntity.class);
    }
}
