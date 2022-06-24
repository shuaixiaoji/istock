package com.github.istock.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.istock.entity.MonthlyAnalysisEntity;
import com.github.istock.entity.StockBaseEntity;
import com.github.istock.entity.StockHisEntity;
import com.github.istock.enums.MarketInterfacesEnums;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.mapper.StockHisMapper;
import com.github.istock.service.StockBaseService;
import com.github.istock.utils.ExcelUtils;
import com.github.istock.utils.TemplateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    public boolean refreshAStock() {
        JSONArray allAStock =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_ZH_A_SPOT_EM.getInterfaceUrl(),
                null);
        List<StockBaseEntity> stockBaseEntityList = StockBaseEntity.convertArrayToBeanList(allAStock);
        stockBaseMapper.batchReplace(stockBaseEntityList);
        return true;
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
        // 本地缓存中获取
        StockBaseEntity entity = stockBaseService.queryByCache(code);
        String name = entity.getName();
        List<StockHisEntity> stockHisEntityList = StockHisEntity.convertArrayToBeanList(monthData,code,name,period);
        System.out.println(stockHisEntityList.size());
        stockHisMapper.insertList(stockHisEntityList);
    }


    @GetMapping("/generate30MonthQuota")
    public String check30MonthQuota(@Param("code") String code) {
        // 1. 从数据库获取数据
        StockHisEntity param = new StockHisEntity();
        param.setCode(code);
        List<StockHisEntity> originDate = stockHisMapper.select(param);
        List<StockHisEntity> sortDate = originDate.stream().sorted(Comparator.comparing(StockHisEntity::getStaticDate)).collect(Collectors.toList());

        // 查询最新价格
        StockHisEntity lastHis = sortDate.get(sortDate.size() - 1);
        BigDecimal recentPrice = lastHis.getClosePrice();
        String name = lastHis.getName();

        // 2. 计算
        List<MonthlyAnalysisEntity> monthlyAnalysisEntities = new ArrayList<>();
        for(int i=30;i<sortDate.size();i++) {
            MonthlyAnalysisEntity monthlyAnalysisEntity = new MonthlyAnalysisEntity();
            // sublist 前毕后开
            BigDecimal month10Avg = sortDate.subList(i-9,i+1).stream().map(StockHisEntity::getClosePrice).reduce(BigDecimal.ZERO,BigDecimal::add).divide(new BigDecimal(10),3,BigDecimal.ROUND_HALF_UP);
            BigDecimal month30Avg = sortDate.subList(i-29,i+1).stream().map(StockHisEntity::getClosePrice).reduce(BigDecimal.ZERO,BigDecimal::add).divide(new BigDecimal(30),3,BigDecimal.ROUND_HALF_UP);
            monthlyAnalysisEntity.setCode(code);
            monthlyAnalysisEntity.setName(name);
            monthlyAnalysisEntity.setRecentPrice(recentPrice);
            monthlyAnalysisEntity.setMonth10Avg(month10Avg);
            monthlyAnalysisEntity.setMonth30Avg(month30Avg);
            monthlyAnalysisEntity.setStaticDate(DateUtil.format(sortDate.get(i).getStaticDate(),"yyyyMMdd"));
            monthlyAnalysisEntities.add(monthlyAnalysisEntity);
        }
        return JSON.toJSONString(monthlyAnalysisEntities);
    }

    @GetMapping("/exportMonthQuota")
    public void checkMonthQuota() {
        List<StockBaseEntity> stockBaseEntityList = stockBaseService.selectListAll();
        List<String> codeList = stockBaseEntityList.stream().map(StockBaseEntity::getCode).collect(Collectors.toList());
        Map<String,StockBaseEntity> map = stockBaseEntityList.stream().collect(Collectors.toMap(StockBaseEntity::getCode,a->a));
        List<MonthlyAnalysisEntity> excelList = new ArrayList<>();
        int count = 0;
        for (String code : codeList) {
            System.out.println(count);
            MonthlyAnalysisEntity entity = checkMonthQuota(code,map);
            if (entity != null) {
                excelList.add(entity);
            }
            count++;
        }
        ExcelUtils.simpleWrite("D://Monthly.xlsx",excelList,MonthlyAnalysisEntity.class);
    }

    @GetMapping("/checkMonthQuota")
    public MonthlyAnalysisEntity checkMonthQuota(@Param("code") String code, Map<String, StockBaseEntity> map) {
        // 1. 实时查询
        Date now = new Date();
        Map<String,String> params = new HashMap<>();
        params.put("symbol",code);
        params.put("period","monthly");
        params.put("start_date",DateUtil.format(DateUtil.offsetMonth(now,-40),"yyyyMMdd"));
        params.put("end_date", DateUtil.format(now,"yyyyMMdd"));
        params.put("adjust","qfq");
        JSONArray monthData =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_ZH_A_HIST.getInterfaceUrl(),
                        params);

        // 需要刷新基础信息表
        StockBaseEntity entity = map.get(code);
        // 查询最新价格
        BigDecimal recentPrice = entity.getRecentPrice();
        String name = entity.getName();
        List<StockHisEntity> sortDate = StockHisEntity.convertArrayToBeanList(monthData,code,name,"monthly");



        // 2. 计算
//        List<MonthlyAnalysisEntity> monthlyAnalysisEntities = new ArrayList<>();
//        for(int i=30;i<sortDate.size();i++) {
//            // TODO 只做最后一次判断，不需要计算所有
//            // 计算所有数据
//            MonthlyAnalysisEntity monthlyAnalysisEntity = new MonthlyAnalysisEntity();
//            // sublist 前毕后开
//            BigDecimal month10Avg = sortDate.subList(i-9,i+1).stream().map(StockHisEntity::getClosePrice).reduce(BigDecimal.ZERO,BigDecimal::add).divide(new BigDecimal(10),3,BigDecimal.ROUND_HALF_UP);
//            BigDecimal month30Avg = sortDate.subList(i-29,i+1).stream().map(StockHisEntity::getClosePrice).reduce(BigDecimal.ZERO,BigDecimal::add).divide(new BigDecimal(30),3,BigDecimal.ROUND_HALF_UP);
//            monthlyAnalysisEntity.setCode(code);
//            monthlyAnalysisEntity.setName(name);
//            monthlyAnalysisEntity.setRecentPrice(recentPrice);
//            monthlyAnalysisEntity.setMonth10Avg(month10Avg);
//            monthlyAnalysisEntity.setMonth30Avg(month30Avg);
//            monthlyAnalysisEntity.setStaticDate(DateUtil.format(sortDate.get(i).getStaticDate(),"yyyyMMdd"));
//            monthlyAnalysisEntities.add(monthlyAnalysisEntity);
//        }
//        return JSON.toJSONString(monthlyAnalysisEntities);

        // 3. 判断是否满足 10月上穿30月，股价回到30月以下
        int count  = sortDate.size();
        // 不足30月不处理
        if (count < 30) {
            return null;
        }
        BigDecimal month10Avg = sortDate.subList(count-10,count).stream().map(StockHisEntity::getClosePrice).reduce(BigDecimal.ZERO,BigDecimal::add).divide(new BigDecimal(10),3,BigDecimal.ROUND_HALF_UP);
        BigDecimal month30Avg = sortDate.subList(count-30,count).stream().map(StockHisEntity::getClosePrice).reduce(BigDecimal.ZERO,BigDecimal::add).divide(new BigDecimal(30),3,BigDecimal.ROUND_HALF_UP);
        if (month10Avg.compareTo(month30Avg) > 0 && recentPrice.compareTo(month30Avg) < 0) {
            MonthlyAnalysisEntity monthlyAnalysisEntity = new MonthlyAnalysisEntity();
            monthlyAnalysisEntity.setCode(code);
            monthlyAnalysisEntity.setName(name);
            monthlyAnalysisEntity.setStaticDate(DateUtil.format(now, "yyyyMMdd"));
            monthlyAnalysisEntity.setMonth30Avg(month30Avg);
            monthlyAnalysisEntity.setMonth10Avg(month10Avg);
            monthlyAnalysisEntity.setRecentPrice(recentPrice);
            monthlyAnalysisEntity.setGapRate(month30Avg.subtract(recentPrice).divide(month30Avg, 3,
                    BigDecimal.ROUND_HALF_UP));
            return monthlyAnalysisEntity;
        }
        return null;
    }
}
