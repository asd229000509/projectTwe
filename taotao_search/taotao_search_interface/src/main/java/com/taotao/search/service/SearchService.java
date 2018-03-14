package com.taotao.search.service;


import com.taotao.search.vo.SolrItem;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    /**
     * 批量导入商品数据到solr
     * @param solrItemList
     */
    void addOrUpdateSolrItemList(List<SolrItem> solrItemList) throws IOException, SolrServerException;
}
