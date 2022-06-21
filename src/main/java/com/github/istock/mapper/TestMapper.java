package com.github.istock.mapper;

import com.github.istock.entity.TestEntity;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 9:51
 */
public interface TestMapper extends Mapper<TestEntity> {
    TestEntity findById(Integer id);
}
