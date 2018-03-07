package com.taotao.manage.controller.impl;


import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item")
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    //TODO 删除
    //TODO 上架
    //TODO 下架

    /**
     * 增加
     * url:'/rest/item',method:'post'
     *
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> savaItem(
            Item item, @RequestParam(value = "desc", required = false) String desc) {
        try {
            itemService.saveItem(item, desc);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    /**
     * 修改
     * url:'/rest/item/update',method:'post'
     *
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> updateItem(
            Item item, @RequestParam(value = "desc", required = false) String desc) {
        try {
            itemService.updateItem(item, desc);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据商品标题模糊分页查询商品列表并且按照更新时间降序排序
     * url:'/rest/item'
     * @param title
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataGridResult> queryItemList(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            //保存商品基本信息和描述信息
            DataGridResult dataGridResult = itemService.queryItemListByTitle(title, page, rows);
            return ResponseEntity.ok(dataGridResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }



}
