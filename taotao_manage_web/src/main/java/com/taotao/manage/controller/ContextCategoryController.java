package com.taotao.manage.controller;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 内容分类
 */
@RequestMapping("/content/category")
@Controller
public class ContextCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory) {
        try {
            contentCategoryService.deleteContentCategory(contentCategory);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 重名名
     * url:'/rest/content/category/update',method:'post'
     * @param contentCategory 要修改的类别
     * @return 返回状态码
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
        try {
            contentCategoryService.updateSelective(contentCategory);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增内容分类
     * url:'/rest/content/category/add',method:'post'
     * @param contentCategory 子内容类别
     * @return 保存好的之内容目录
     */
    @RequestMapping("/add")
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
        try {
            ContentCategory result = contentCategoryService.saveContentCategory(contentCategory);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据内容分类父id 查询子节点
     * url:'/rest/content/category',method:'get'
     * @param parentId 内容分类父id
     * @return 返回easyUi tree 需要的json格式数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
            @RequestParam(value = "id",defaultValue = "0")Long parentId){
        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setParentId(parentId);
            List<ContentCategory> list = contentCategoryService.queryListByWhere(contentCategory);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
