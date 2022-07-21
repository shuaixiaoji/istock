package com.github.istock.service;

import com.github.istock.entity.LowerShadowEntity;
import com.github.istock.entity.StockBaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StockBaseService extends BaseService<StockBaseEntity> {

    StockBaseEntity queryByCache(String code);

    void batchReplace(List<StockBaseEntity> entities);

    List<StockBaseEntity> queryAllStock();

    BigDecimal queryTotalStock(String code);

    LowerShadowEntity checkLowerShadow(String code, Map<String, StockBaseEntity> map, Integer countDay,BigDecimal rate);
}
