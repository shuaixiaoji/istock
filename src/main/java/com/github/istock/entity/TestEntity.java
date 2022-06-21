package com.github.istock.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/13 14:43
 */
@Data
@Table(name = "test")
public class TestEntity {
    private String name;
    private Integer id;
}
