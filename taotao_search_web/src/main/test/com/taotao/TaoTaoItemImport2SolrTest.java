package com.taotao;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.search.service.SearchService;
import com.taotao.search.vo.SolrItem;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaoTaoItemImport2SolrTest {
    private SearchService searchService;
    private ItemService itemService;

    @Before
    public void setUp() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        searchService = context.getBean(SearchService.class);
        itemService = context.getBean(ItemService.class);
    }


    @Test
    public void test() throws IOException, SolrServerException {
        Integer page = 1;
        Integer pageSize = 500;
        List<Item> items = null;
        List<SolrItem> solrItems = null;
        SolrItem solrItem = null;
        do {
            System.out.println("---------正在导入第" + page + "页...");
            //获取商品列表
            items = itemService.queryListByPage(null, page, pageSize);
            if (items != null) {
                //将商品列表对象转化为SolrItem对象列表
                solrItems = new ArrayList<>();
                for (Item item : items) {
                    solrItem = new SolrItem();
                    solrItem.setId(item.getId());
                    solrItem.setTitle(item.getTitle());
                    solrItem.setImage(item.getImage());
                    solrItem.setPrice(item.getPrice());
                    solrItem.setSellPoint(item.getSellPoint());
                    solrItem.setStatus(item.getStatus());

                    solrItems.add(solrItem);
                }
                //添加到solr中
                searchService.addOrUpdateSolrItemList(solrItems);
                System.out.println("----------导入第" + page + "页完成。");
                page++;
                pageSize = items.size();
            }
        } while (pageSize == 500);
    }
}
