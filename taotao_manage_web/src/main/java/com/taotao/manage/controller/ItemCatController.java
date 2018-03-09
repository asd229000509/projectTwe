package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * /rest/item/cat/query/1?rows=10
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/query/{page}")
    public ResponseEntity<List<ItemCat>> queryItemCatListByPage(
            @PathVariable("page") Integer page, @RequestParam(value = "rows", defaultValue = "20") Integer rows) {
        try {
            //请用服务层对象查询数据
            //List<ItemCat> lists = itemCatService.queryItemCatListByPage(page, rows);
            List<ItemCat> lists = itemCatService.queryListByPage(null, page, rows);
            return ResponseEntity.ok(lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据父目录id查询子目录
     * url:'/rest/item/cat',method:'GET'
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListParentId(
            @RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {
            //调用服务层对象查询对象
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> itemCats = itemCatService.queryListByWhere(itemCat);
            return ResponseEntity.ok(itemCats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据商品类目id 获取商品类目
     * url:'/rest/item/desc/'+data.id
     * @param itemCatId 商品id
     * @return
     */
    @RequestMapping(value = "/{itemCatId}",method = RequestMethod.GET)
    public ResponseEntity<ItemCat> queryItemCatById(@PathVariable("itemCatId") Long itemCatId) {
        try {
            ItemCat itemCat = itemCatService.queryById(itemCatId);
            return ResponseEntity.ok(itemCat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
