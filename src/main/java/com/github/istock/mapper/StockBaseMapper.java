package com.github.istock.mapper;

import com.github.istock.entity.StockBaseEntity;
import com.github.istock.entity.TestEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 9:51
 */
public interface StockBaseMapper extends Mapper<StockBaseEntity>, MySqlMapper<StockBaseEntity> {

}
