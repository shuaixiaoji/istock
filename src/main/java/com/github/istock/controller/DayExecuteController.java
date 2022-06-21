package com.github.istock.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.istock.entity.StockBaseEntity;
import com.github.istock.enums.MarketInterfacesEnums;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.utils.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shuaixiaoji
 * @Description  需每日执行方法
 * @date 2022/6/21 14:39
 */
@RestController
@RequestMapping("dayExecute")
public class DayExecuteController {

    @Autowired
    private StockBaseMapper stockBaseMapper;

    @GetMapping("/refreshAStock")
    public void getName() {
        JSONArray allAStock =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_ZH_A_SPOT_EM.getInterfaceUrl(),
                null);
        List<StockBaseEntity> stockBaseEntityList = StockBaseEntity.convertArrayToBeanList(allAStock);
        System.out.println(stockBaseEntityList.size());
        stockBaseMapper.insertList(stockBaseEntityList);
    }
}
