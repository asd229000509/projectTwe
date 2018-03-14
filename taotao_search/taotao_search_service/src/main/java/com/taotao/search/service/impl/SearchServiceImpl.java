package com.taotao.search.service.impl;



import com.taotao.search.service.SearchService;
import com.taotao.search.vo.SolrItem;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private CloudSolrServer cloudSolrServer;

    @Override
    public void addOrUpdateSolrItemList(List<SolrItem> solrItemList) throws IOException, SolrServerException {
        //添加或根系到索引库列表
        cloudSolrServer.addBeans(solrItemList);
        //提交
        cloudSolrServer.commit();
    }
}
