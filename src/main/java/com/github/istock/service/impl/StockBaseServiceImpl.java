package com.github.istock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.istock.entity.StockBaseEntity;
import com.github.istock.enums.MarketInterfacesEnums;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.service.StockBaseService;
import com.github.istock.utils.TemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StockBaseServiceImpl extends BaseServiceImpl<StockBaseMapper,StockBaseEntity> implements StockBaseService {

    @Override
    @Cacheable(value = "cache2Minute",key = "#code")
    public StockBaseEntity queryByCache(String code) {
        StockBaseEntity param = new StockBaseEntity();
        param.setCode(code);
        return mapper.selectOne(param);
    }

    @Override
    public void batchReplace(List<StockBaseEntity> entities) {
        mapper.batchReplace(entities);
    }

    @Override
    @Cacheable(value = "cache2Minute")
    public List<StockBaseEntity> queryAllStock() {
        JSONArray allAStock =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_ZH_A_SPOT_EM.getInterfaceUrl(),
                        null);
        List<StockBaseEntity> stockBaseEntityList = StockBaseEntity.convertArrayToBeanList(allAStock);
        return stockBaseEntityList;
    }

    @Override
    public BigDecimal queryTotalStock(String code) {
        Map<String,String> params = new HashMap<>();
        params.put("symbol",code);
        JSONArray baseInfoArray =
                TemplateUtils.requestForJsonArray(MarketInterfacesEnums.STOCK_INDIVIDUAL_INFO_EM.getInterfaceUrl(),
                        params);
        for (Object info : baseInfoArray) {
            if (JSON.parseObject(JSON.toJSONString(info)).getString("item").equals("总股本")) {
                return JSON.parseObject(JSON.toJSONString(info)).getBigDecimal("value");
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 使用组装SQL，大数据insert提高效率
     *
     * @param map
     * @return
     */
    public String generateBatchSql(Map map) {
        List<StockBaseEntity> list = (List<StockBaseEntity>) map.get("list");
        StringBuffer sqlList = new StringBuffer();
        sqlList.append(" REPLACE INTO stock_base_info(code,name,recent_price,per,pbr,total_amount,circulation_amount,gain60,gain_year) VALUES ");
        for (int i = 0; i < list.size() ; i++) {
            StockBaseEntity user = list.get(i);
            sqlList.append(" (").
                     append("'").append(user.getCode()).append("'").append(",")
                    .append("'").append(user.getName()).append("',").append(user.getRecentPrice())
                    .append(",").append(user.getPer()).append(",").append(user.getPbr())
                    .append(",").append(user.getTotalAmount())
                    .append(",").append(user.getCirculationAmount())
                    .append(",").append(user.getGain60())
                    .append(",").append(user.getGainYear())
                    .append(")");
            if (i < list.size()-1) {
                sqlList.append(",");
            }
        }
        return sqlList.toString();
    }

}
