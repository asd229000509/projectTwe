package com.taotao.manage.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.commom.vo.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Value("${CONTENT_CATEGORY_BIG_AD_ID}")
    private Long CONTENT_CATEGORY_BIG_AD_ID;
    @Value("${PORTAL_INDEX_BIG_AD_NUMBER}")
    private  Integer PORTAL_INDEX_BIG_AD_NUMBER;
    //大广告数据在redis中的key
    private static final String REDIS_BIG_AD_KEY = "PORTAL_INDEX_BIG_AD_DATA";
    //大广告数据在redis中的过期时间；1天
    private static final int REDIS_BIG_AD_EXPIRE_TIME = 60*60*24;

    @Autowired
    private RedisService redisService;
    @Autowired
    private ContentMapper contentMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();



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

    /**
     * 获取门户系统首页大广告
     * 分页查询第一页6条数据,并且根据更新时间倒序排序
     *
     * @return 返回json格式
     * @throws Exception
     */
    @Override
    public String queryPortalBigAdData() throws Exception {
        String str = "";

        try {
            str = redisService.get(REDIS_BIG_AD_KEY);
            if (StringUtils.isNotBlank(str)) {
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询首页大广告
        DataGridResult dataGridResult = queryContentListByParentIdPage(CONTENT_CATEGORY_BIG_AD_ID, 1, PORTAL_INDEX_BIG_AD_NUMBER);
        List<Content> contentList = (List<Content>) dataGridResult.getRows();
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, java.lang.Object> map = null;
        if (contentList != null) {
            for (Content content : contentList) {
                map = new HashMap<>();
                map.put("alt", 1);
                map.put("height",240 );
                map.put("href", content.getUrl());
                map.put("src", content.getPic());
                map.put("srcB", content.getPic());
                map.put("width", 670);
                map.put("widthB", 550);
                resultList.add(map);
            }
        }

        //转为字符串
        str = MAPPER.writeValueAsString(resultList);
        try {
            //存入redis
            redisService.setex(REDIS_BIG_AD_KEY, REDIS_BIG_AD_EXPIRE_TIME, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


}
