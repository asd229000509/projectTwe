package com.taotao.manage.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
    //查询全部
    List<T> queryAll();

    //根据主键查询
    T queryById(Serializable id);

    //根据条件查询记录总数
    Integer queryCountByWhere(T t);

    //根据条件查询列表
    List<T> queryListByWhere(T t);

    //分页查询列表
    List<T> queryListByPage(T t, Integer page, Integer rows);

    //选择性新增
    void saveSelective(T t);

    //选择性更新
    void updateSelective(T t);

    //根据id删除
    void deleteById(Serializable id);

    //根据id集合删除
    void deleteByIds(Serializable[] ids);
}
