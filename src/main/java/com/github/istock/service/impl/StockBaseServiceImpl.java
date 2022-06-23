package com.github.istock.service.impl;

import com.github.istock.entity.StockBaseEntity;
import com.github.istock.mapper.StockBaseMapper;
import com.github.istock.service.StockBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockBaseServiceImpl extends BaseServiceImpl<StockBaseMapper,StockBaseEntity> implements StockBaseService {

    @Override
    @Cacheable(value = "cache2Hour",key = "#code")
    public StockBaseEntity queryByCache(String code) {
        log.error("1111");
        return new StockBaseEntity();
    }
}
