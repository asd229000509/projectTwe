package com.taotao.manage.service;

import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.pojo.Content;

import java.util.List;

public interface ContentService extends BaseService<Content>{

    DataGridResult queryContentListByParentIdPage(Long categoryId, Integer page, Integer rows) ;

    /**
     * 获取门户系统首页大广告
     * @return 返回json格式
     * @throws Exception
     */
    String queryPortalBigAdData() throws Exception;
}
