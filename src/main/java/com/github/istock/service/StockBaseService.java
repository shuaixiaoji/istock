package com.github.istock.service;

import com.github.istock.entity.StockBaseEntity;

import java.util.List;

public interface StockBaseService extends BaseService<StockBaseEntity> {

    StockBaseEntity queryByCache(String code);

    void batchReplace(List<StockBaseEntity> entities);
}
