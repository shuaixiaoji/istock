package com.github.istock.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author shuaixiaoji
 * @Description 退市excel格式
 * @date 2022/6/16 10:13
 */
@Data
public class StopEntity {
    @ExcelProperty("序号")
    private Integer index;

    @ExcelProperty("代码")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("昨收")
    private String yesPrice;

    public static StopEntity convertJsonToBean(JSONObject json){
        StopEntity stopEntity  = new StopEntity();
        stopEntity.setIndex(json.getInteger("序号"));
        stopEntity.setCode(json.getString("代码"));
        stopEntity.setName(json.getString("名称"));
        stopEntity.setYesPrice(json.getString("昨收"));
        return stopEntity;
    }
}
