package com.taotao.manage.service;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;


    /**
     * 新增
     * @param item 基本信息
     * @param desc 描述信息
     * @return
     */
    @Override
    public Long saveItem(Item item, String desc) {
        //保存商品
        saveSelective(item);

        //保存描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(item.getId());
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(itemDesc.getCreated());

        itemDescMapper.insertSelective(itemDesc);
        return item.getId();
    }

    /**
     * 修改
     * @param item 基本信息
     * @param desc 描述信息
     */
    @Override
    public void updateItem(Item item, String desc) {
        //保存商品
        updateSelective(item);

        //保存描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(item.getId());
        itemDesc.setUpdated(new Date());
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }
}
