package com.github.istock.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.istock.entity.StockBaseEntity;
import com.github.istock.entity.StockHisEntity;
import com.github.istock.enums.MarketInterfacesEnums;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.mapper.StockHisMapper;
import com.github.istock.service.StockBaseService;
import com.github.istock.utils.TemplateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private StockHisMapper stockHisMapper;
    @Autowired
    private StockBaseService stockBaseService;

    @GetMapping("/refreshAStock")
    public void refreshAStock() {
        JSONArray allAStock =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_ZH_A_SPOT_EM.getInterfaceUrl(),
                null);
        List<StockBaseEntity> stockBaseEntityList = StockBaseEntity.convertArrayToBeanList(allAStock);
        System.out.println(stockBaseEntityList.size());
        stockBaseMapper.insertList(stockBaseEntityList);
    }

    /**
     * 根据股票编码刷新月数据
     *
     * @param code 股票编码
     */
    @GetMapping("/refreshMonthData")
    public void refreshMonthData(@Param("code") String code,@Param("period") String period){
        Date now = new Date();
        Map<String,String> params = new HashMap<>();
        params.put("symbol",code);
        params.put("period",period);
        params.put("start_date",DateUtil.format(DateUtil.offsetMonth(now,-99),"yyyyMMdd"));
        params.put("end_date", DateUtil.format(now,"yyyyMMdd"));
        params.put("adjust","qfq");
        JSONArray monthData =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_ZH_A_HIST.getInterfaceUrl(),
                        params);
        // TODO 本地缓存中获取
        StockBaseEntity entity = stockBaseService.queryByCache(code);
        String name = entity.getName();
        List<StockHisEntity> stockHisEntityList = StockHisEntity.convertArrayToBeanList(monthData,code,name,period);
        System.out.println(stockHisEntityList.size());
        stockHisMapper.insertList(stockHisEntityList);
    }
}
