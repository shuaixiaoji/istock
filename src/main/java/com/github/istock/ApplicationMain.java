package com.github.istock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.istock.entity.StopEntity;
import com.github.istock.enums.InterfacesEnums;
import com.github.istock.utils.ExcelUtils;
import com.github.istock.utils.Template;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/13 14:54
 */
public class ApplicationMain {

    public static void main(String[] args) {
        // 接口名 + map格式参数获取结果
        JSONArray resultArray = Template.requestForJsonArray(InterfacesEnums.STOCK_ZH_A_STOP_EM.getInterfaceUrl(),
                null);

        List<StopEntity> stopList = resultArray.stream().map(object -> {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
            return StopEntity.convertJsonToBean(json);
        }).collect(Collectors.toList());

        ExcelUtils.simpleWrite("D://stopExcel.xlsx", stopList, StopEntity.class);
    }

}
