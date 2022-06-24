package com.github.istock.service.impl;

import com.github.istock.entity.StockBaseEntity;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.service.StockBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
