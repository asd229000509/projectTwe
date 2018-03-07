package com.taotao.manage.service;

import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item>{

    /**
     * 保存商品基本信息、描述信息到数据库中
     * @param item 基本信息
     * @param desc 描述信息
     * @return 保存条数
     */
    Long saveItem(Item item, String desc);

    /**
     * 修改数据
     * @param item 基本信息
     * @param desc 描述信息
     */
    void updateItem(Item item, String desc);

    /**
     * 根据商品标题模糊分页查询商品列表
     * @param title 标题
     * @param page 页号
     * @param rows 页大小
     * @return dataGrid插件需要的分页显示数据
     */
    DataGridResult queryItemListByTitle(String title, Integer page, Integer rows);
}
