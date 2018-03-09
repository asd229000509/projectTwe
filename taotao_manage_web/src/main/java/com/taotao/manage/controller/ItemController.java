package com.taotao.manage.controller;


import com.taotao.commom.vo.DataGridResult;
import com.taotao.manage.pojo.Item;
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


    @RequestMapping("/delete")
    public ResponseEntity<Void> deleteItemByIds(@RequestParam("ids") Long[] itemIds) {
        try {
            itemService.deleteByIds(itemIds);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 商品上架
     * @param itemIds 商品id集合
     * @return 返回状态码
     */
    @RequestMapping("/reshelf")
    public ResponseEntity<Void> reshelf(@RequestParam("ids") String itemIds) {
        try {

            itemService.updateItemStatusByIds(itemIds,1);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 商品下架
     * @param itemIds 商品id 集合
     * @return 返回状态码
     */
    @RequestMapping("/instock")
    public ResponseEntity<Void> instock(@RequestParam("ids") String itemIds) {
        try {
            itemService.updateItemStatusByIds(itemIds,2);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    /**
     * 增加
     * url:'/rest/item',method:'post'
     *
     * @param item 商品的对象
     * @param desc
     * @return 返回状态码
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> savItem(
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
     * @return 返回状态码
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
     * @return 返回dataGrid需要的分页信息
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
