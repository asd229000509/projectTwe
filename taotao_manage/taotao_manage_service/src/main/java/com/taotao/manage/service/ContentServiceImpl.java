package com.taotao.manage.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService  {

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public DataGridResult queryContentListByParentIdPage(Long categoryId, Integer page, Integer rows) {
        //设置分页查询
        PageHelper.startPage(page, rows);
        Example example = new Example(Content.class);
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andEqualTo("categoryId", categoryId);
        //排序
        example.orderBy("update").desc();
        //查询
        List<Content> lists = contentMapper.selectByExample(example);
        PageInfo<Content> info = new PageInfo<>(lists);
        return new DataGridResult(info.getTotal(), info.getList());
    }




}
