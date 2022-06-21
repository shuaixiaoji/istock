package com.github.istock.controller;

import com.github.istock.entity.TestEntity;
import com.github.istock.service.TestService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/21 9:56
 */
@RestController
public class TestConnectDbController {

    @Autowired
    private TestService service;


    @GetMapping("/getName")
    public String getName(@Param("id") Integer id){
        TestEntity tMsg = service.findById(id);
        return tMsg.getName();
    }
}
