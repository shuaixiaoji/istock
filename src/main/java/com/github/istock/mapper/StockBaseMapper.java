package com.github.istock.mapper;

import com.github.istock.entity.StockBaseEntity;
import com.github.istock.service.impl.StockBaseServiceImpl;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 9:51
 */
public interface StockBaseMapper extends Mapper<StockBaseEntity>, MySqlMapper<StockBaseEntity> {
    @InsertProvider(type = StockBaseServiceImpl.class, method = "generateBatchSql")
    void batchReplace(@Param("list") List<StockBaseEntity> stockBaseEntityList);
}
