package com.github.istock.service.impl;


import com.github.istock.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public class BaseServiceImpl<M extends Mapper<T>, T> implements BaseService<T> {
    @Autowired
    protected M mapper;


    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

//    @Override
//    public List<T> selectListByIds(List<Object> ids) {
//        return mapper.selectByIds(ids);
//    }

    @Override
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public List<T> selectListAll() {
        return mapper.selectAll();
    }

//    @Override
//    public Long selectCountAll() {
//        return mapper.selectCount();
//    }

    @Override
    public Long selectCount(T entity) {
        return Long.valueOf(mapper.selectCount(entity));
    }

    @Override
    public Integer insert(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public Integer insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public Integer delete(T entity) {
        return mapper.delete(entity);
    }

    @Override
    public Integer deleteById(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateById(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public Integer updateSelectiveById(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

}
