package com.taotao.manage.controller.impl;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/item/desc")
@Controller
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 根据商品id获取商品的描述信息
     * @param itemId 商品id
     * @return 商品描述信息
     */
    @RequestMapping("/{itemId}")
    public ResponseEntity<ItemDesc> queryItemDescById(@PathVariable("itemId") Long itemId) {
        try {
            ItemDesc itemDesc = itemDescService.queryById(itemId);
            return ResponseEntity.ok(itemDesc);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
