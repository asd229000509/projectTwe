package com.taotao.manage.service;

import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.pojo.Content;

import java.util.List;

public interface ContentService extends BaseService<Content>{

    DataGridResult queryContentListByParentIdPage(Long categoryId, Integer page, Integer rows) ;
}
