package com.taotao.manage.controller;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item/interface")
@RestController
public class ItemInterfaceController {

    @Autowired
    private ItemService itemService;

    /**
     * 保存商品
     *
     * @param item
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item) {
        try {
            itemService.saveSelective(item);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<Item> queryItemById(@PathVariable Long itemId) {
        try {
            if (itemId > 0) {
                Item item = itemService.queryById(itemId);
                return ResponseEntity.ok(item);
            } else {
                //返回400,表示参数错误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果报错返回http响应码500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    /**
     * 更新商品
     * @param item
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item) {
        try {
            itemService.updateSelective(item);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除商品
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteItem(@RequestParam("ids")Long[] ids) {
        try {
            if (ids != null && ids.length >0 ) {
                itemService.deleteByIds(ids);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
