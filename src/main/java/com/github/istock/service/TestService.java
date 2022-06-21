package com.github.istock.service;

import com.github.istock.entity.TestEntity;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 11:05
 */
public interface TestService {
    TestEntity findById(Integer id);
}
