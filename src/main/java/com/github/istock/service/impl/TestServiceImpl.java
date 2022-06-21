package com.github.istock.service.impl;

import com.github.istock.entity.TestEntity;
import com.github.istock.mapper.TestMapper;
import com.github.istock.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 11:15
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<TestMapper,TestEntity> implements TestService {

    @Override
    public TestEntity findById(Integer id) {
        return mapper.findById(id);
    }
}
