package com.taotao.manage.service;

import com.taotao.manage.pojo.ItemCat;

import java.util.List;

public interface ItemCatService extends BaseService<ItemCat>{
    //分页查询
    @Deprecated
    List<ItemCat> queryItemCatListByPage(Integer page, Integer rows);
}
