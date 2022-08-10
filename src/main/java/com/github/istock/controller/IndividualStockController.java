package com.github.istock.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.istock.constants.Constant;
import com.github.istock.entity.*;
import com.github.istock.enums.MarketInterfacesEnums;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.mapper.StockHisMapper;
import com.github.istock.service.StockBaseService;
import com.github.istock.utils.ExcelUtils;
import com.github.istock.utils.TemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author shuaixiaoji
 * @Description 需每日执行方法
 * @date 2022/6/21 14:39
 */
@RestController
@Slf4j
@RequestMapping("individual")
public class IndividualStockController {

    @Autowired
    private StockBaseService stockBaseService;


    /**
     * 根据股票编码查看当日20条新闻
     *
     * @param code 股票编码
     */
    @GetMapping("/queryNewsByCode")
    public void queryNewsByCode(@Param("code") String code) {
        Map<String, String> params = new HashMap<>();
        params.put("stock", code);
        JSONArray newsData =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_NEWS.getInterfaceUrl(),
                        params);
        // 本地缓存中获取
        StockBaseEntity entity = stockBaseService.queryByCache(code);
        String name = entity.getName();
        List<StockNewsEntity> newsList = StockNewsEntity.convertArrayToBeanList(newsData, code, name);
        // 导出excel
        ExcelUtils.simpleWrite("D://NEWS.xlsx", newsList, StockNewsEntity.class);
    }
}
