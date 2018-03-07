package com.taotao.manage.controller.impl;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(value = "id", defaultValue = "0")Long parentId) {
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

}
