package com.taotao.manage.service;


import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentCategoryServiceImpl
        extends BaseServiceImpl<ContentCategory> implements ContentCategoryService  {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    /**
     *
     * @param contentCategory 子内容目录
     * @return 保存好的之内容目录
     */
    @Override
    public ContentCategory saveContentCategory(ContentCategory contentCategory) {
        //1、保存节点数据
        contentCategory.setIsParent(false);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        saveSelective(contentCategory);

        //修改父内容类别状态
        ContentCategory parent = new ContentCategory();
        parent.setId(contentCategory.getParentId());
        parent.setIsParent(true);
        updateSelective(parent);
        return contentCategory;
    }

    /**
     * 删除节点：删除当前节点及其子孙节点，之后再判断其父节点是是否为父节点（根据父节点查询其是否还有其他子节点，如果还有则还为父节点
     * 不需要做任何修改，如果没有其它子节点则修改父节点的isParent为false）
     * @param contentCategory 子内容目录
     */
    @Override
    public void deleteContentCategory(ContentCategory contentCategory) {
        //1、删除该类目及其所有子类目
        List<Long> ids = new ArrayList<>();
        ids.add(contentCategory.getId());
        getCategoryIds(ids, contentCategory.getId());
        //删除节点
        deleteByIds(ids.toArray(new Long[]{}));

        //2、更新该类目的父类目（如果其父类目还有其他子类目则还是父类目，否则不是父类目）
        ContentCategory param = new ContentCategory();
        param.setParentId(contentCategory.getParentId());
        Integer count = queryCountByWhere(param);
        if(count == 0){
            //说明父节点已经没其它子节点，这时应该将父节点变为非父节点（isParent=false）
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            updateSelective(parent);
        }
    }

    /**
     * 获取id对应的节点的所有子孙节点id
     * @param ids 所有节点的id集合
     * @param id 节点id
     */
    private void getCategoryIds(List<Long> ids, Long id) {
        ContentCategory category = new ContentCategory();
        category.setParentId(id);
        List<ContentCategory> list = queryListByWhere(category);
        if (list != null && list.size() > 0) {
            for (ContentCategory contentCategory : list) {
                ids.add(contentCategory.getId());
                getCategoryIds(ids, contentCategory.getId());
            }
        }
    }
}
