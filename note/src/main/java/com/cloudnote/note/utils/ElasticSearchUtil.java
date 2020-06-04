package com.cloudnote.note.utils;


import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class ElasticSearchUtil {


    /**
     * 查询
     * @param client 客户端
     * @param index 索引
     * @param boolQuery 复合查询
     * @return 返回查询结果
     * @throws IOException
     */
    public static SearchHits query(RestHighLevelClient client,String index,BoolQueryBuilder boolQuery) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQuery);
        SearchRequest searchRequest = new SearchRequest(index); //索引
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return response.getHits();//SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
    }


    /**
     * 查询
     * @param client 客户端
     * @param index 索引
     * @param id id
     * @return 查询到结果返回Map；否则返回null
     */
    public static Map<String,Object> documentQuery(RestHighLevelClient client,String index,String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse response = null;
        response = client.get(getRequest, RequestOptions.DEFAULT);
        return response.getSourceAsMap();
    }



        /**
         * 更新
         * @param client 客户端
         * @param indexName 索引
         * @param id id
         * @param data 数据
         * @return 成功1；失败0
         */
    public static int documentUpdate(RestHighLevelClient client,String indexName,String id, Map<String, Object> data) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(indexName, id);
        updateRequest.doc(data);
        client.update(updateRequest, RequestOptions.DEFAULT);
        return 1;
    }
}
