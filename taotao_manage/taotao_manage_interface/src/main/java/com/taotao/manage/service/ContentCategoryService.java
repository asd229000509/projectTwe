package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;

public interface ContentCategoryService extends BaseService<ContentCategory> {
    /**
     * 新增内容类别
     * @param contentCategory  子内容目录
     * @return 保存好的之内容目录
     */
    ContentCategory saveContentCategory(ContentCategory contentCategory);

    /**
     * 删除节点：删除当前节点及其子孙节点，之后再判断其父节点是是否为父节点（根据父节点查询其是否还有其他子节点，如果还有则还为父节点
     * 不需要做任何修改，如果没有其它子节点则修改父节点的isParent为false）
     * @param contentCategory 子内容目录
     */
    void deleteContentCategory(ContentCategory contentCategory);
}
