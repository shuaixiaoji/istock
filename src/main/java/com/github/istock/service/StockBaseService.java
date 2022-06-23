package com.github.istock.service;

import com.github.istock.entity.StockBaseEntity;

public interface StockBaseService extends BaseService<StockBaseEntity> {

    StockBaseEntity queryByCache(String code);
}
