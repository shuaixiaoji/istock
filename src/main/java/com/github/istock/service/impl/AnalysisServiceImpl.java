package com.github.istock.service.impl;

import com.github.istock.ExportUtil;
import com.github.istock.entity.AnalysisHoldRankEntity;
import com.github.istock.entity.AnalystDetailEntity;
import com.github.istock.entity.AnalystRankEntity;
import com.github.istock.enums.InterfacesEnums;
import com.github.istock.service.AnalysisService;
import com.github.istock.utils.TemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AnalysisServiceImpl
 *
 * @author mac-huanglc
 * @since 2022/6/22
 */
@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {
    
    @Override
    public void exportAnalysisData() {
        String url = InterfacesEnums.STOCK_ANALYST_RANK_EM.getInterfaceUrl();
        Map<String, String> query = new HashMap<>();
        //        query.put("analyst_id", "11000284133");
        List<Object> list = TemplateUtils.requestForList(url, query);
        //        ExportUtil.exportData(list, InterfacesEnums.STOCK_ANALYST_DETAIL_EM.getInterfaceUrl());

        Map<String, List<Object>> dataMap = new HashMap<>();
        List<Object> newList = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        List<AnalystDetailEntity> allDetailList = new ArrayList<>();
        for (Object obj : list) {
            AnalystRankEntity entity = AnalystRankEntity.convertJsonToBean(obj);
            newList.add(entity);

            Map<String, String> params = new HashMap<>();
            params.put("analyst_id", entity.getNo());

            String detailUrl = InterfacesEnums.STOCK_ANALYST_DETAIL_EM.getInterfaceUrl();
            List<Object> detailList = new ArrayList<>();
            try {
                detailList = TemplateUtils.requestForList(detailUrl, params);
            } catch (Exception exception) {
                log.info("STOCK_ANALYST_DETAIL_EM invoke error. id = {}", entity.getNo());
            }
            if (CollectionUtils.isEmpty(detailList)) {
                continue;
            }
            // 2.分析师详情
            for (Object detail : detailList) {
                AnalystDetailEntity detailEntity = AnalystDetailEntity.convertJsonToBean(detail);
                detailEntity.setId(entity.getNo());
                detailEntity.setName(entity.getName());
                data.add(detailEntity);
                allDetailList.add(detailEntity);
            }
            dataMap.put(detailUrl, data);
        }
        dataMap.put(url, newList);

        Map<String, Long> numMap = allDetailList.stream()
            .collect(Collectors.groupingBy(AnalystDetailEntity::getStockCode, Collectors.counting()));
        List<Object> numData = new ArrayList<>();
        // 3.分析师持有股排序
        for (String key : numMap.keySet()) {
            AnalysisHoldRankEntity entity = new AnalysisHoldRankEntity();
            entity.setStockCode(key);
            entity.setNum(numMap.get(key));
            numData.add(entity);
        }
        dataMap.put("stock_analyst_hold_rank", numData);
        
        ExportUtil.exportData(dataMap);
    }
}
