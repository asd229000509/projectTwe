package com.taotao.manage.service;


import com.github.pagehelper.PageHelper;
import com.taotao.manage.pojo.BasePojo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    private Class<T> clazz;

    public BaseServiceImpl() {
        //this表示当前被实例化的对象；如UserServiceImpl--->BaseServiceImpl<User>
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    @Override
    public T queryById(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer queryCountByWhere(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> queryListByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public List<T> queryListByPage(T t,Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return mapper.select(t);
    }

    @Override
    public void saveSelective(T t) {
        if (t.getCreated() == null) {
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
        } else if (t.getUpdated() == null) {
            t.setUpdated(t.getCreated());
        }
        mapper.insertSelective(t);
    }

    @Override
    public void updateSelective(T t) {
        if (t.getUpdated() == null) {
            t.setUpdated(t.getCreated());
        }
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteById(Serializable id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Serializable[] ids) {
        //设置删除条件
        Example example = new Example(this.clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        mapper.deleteByExample(example);
    }
}
