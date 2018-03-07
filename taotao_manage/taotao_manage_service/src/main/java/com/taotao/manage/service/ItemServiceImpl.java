package com.taotao.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;


    /**
     * 新增
     *
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
     *
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

    /**
     * 根据title模糊查询,分页显示并且根据updated降序排序
     * @param title 标题
     * @param page  页号
     * @param rows  页大小
     * @return
     */
    @Override
    public DataGridResult queryItemListByTitle(String title, Integer page, Integer rows) {
        //创建Example
        Example example = new Example(Item.class);
        try {
            if (StringUtils.isNoneBlank(title)) {
                //添加查询条件
                Example.Criteria criteria = example.createCriteria();
                //解码
                title = URLDecoder.decode(title, "utf-8");
                criteria.andLike("title", "%" + title + "%");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //根据更时间降序排序
        example.orderBy("updated").desc();
        //设置分页
        PageHelper.startPage(page, rows);
        //分页查询
        List<Item> items = itemMapper.selectByExample(example);
        //转换为分页信息对象
        PageInfo<Item> pageInfo = new PageInfo<>(items);
        //返回DataGridResult
        return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
