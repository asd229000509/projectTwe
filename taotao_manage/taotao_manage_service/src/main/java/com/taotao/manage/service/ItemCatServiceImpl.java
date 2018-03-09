package com.taotao.manage.service;


import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService  {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {
        //设置分页查询
        PageHelper.startPage(page, rows);
        //查询
        List<ItemCat> lists = itemCatMapper.selectAll();
        return lists;
    }




}
