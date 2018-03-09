package com.taotao.manage.controller;

import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/content")
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataGridResult> queryContentListByCategoryId(
            @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows) {
        try {
            DataGridResult result = contentService.queryContentListByParentIdPage(categoryId, page, rows);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    /**
     * 添加内容
     * @param content 添加的内容对象
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content) {
        try {
            contentService.saveSelective(content);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新内容
     * @param content 更新的内容对象
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ResponseEntity<Void> updateContent(Content content) {
        try {
            contentService.updateSelective(content);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 批量删除
     * @param ids 删除的的内容对象集合
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> deleteContent(@RequestParam("ids") Long[] ids) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 500);
        try {
            contentService.deleteByIds(ids);
            result.put("status", 200);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

}
