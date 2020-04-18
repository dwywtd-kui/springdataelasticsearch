package com.inspur.springdataelasticsearch.multiindex;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/9
 */
@Service
public class ElasticsearchService {

    @Autowired
    TransportClient client;


    Boolean initMapping(Map<String,Integer> map,String name){
        XContentBuilder xContentBuilder=null;
        try {
            xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("properties")
                            .startObject("id")
                                .field("type","keyword")
                            .endObject()
                            .startObject("title")
                                .field("type","text")
                                .field("analyzer","ik_smart")
                            .endObject()
                            .startObject("text")
                                .field("type","text")
                                .field("analyzer","ik_smart")
                            .endObject()
                            .startObject("creationtime")
                                .field("type","date")
                            .endObject()
                            .startObject("creationuserid")
                                .field("type","keyword")
                            .endObject()
                            .startObject("type")
                                .field("type","keyword")
                            .endObject()
                            .startObject("releaserange")
                                .field("type","text")
                                .field("analyzer","ik_smart")
                            .endObject();
//                        .endObject()
//                    .endObject();
            Set<Map.Entry<String, Integer>> entries = map.entrySet();
            for (Map.Entry<String,Integer> entry:entries){
                String key = entry.getKey();
                if ("id".equals(key))
                    continue;
                if ("title".equals(key))
                    continue;
                if ("text".equals(key))
                    continue;
                if ("type".equals(key))
                    continue;
                Integer value = entry.getValue();
                switch (value) {
                    case 1: xContentBuilder.startObject(key)
                                .field("type", "keyword")
                                .endObject();break;
                    case 2: xContentBuilder.startObject(key)
                            .field("type","text")
                            .field("analyzer","ik_smart")
                            .endObject();break;
                    default: break;
                }
            }
            xContentBuilder.endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate("h_" + name).execute().actionGet();//创建一个空索引，如没有索引，创建mapping时会报错
        if (createIndexResponse.isAcknowledged()) {
            PutMappingRequest putMappingRequest = Requests.putMappingRequest("h_" + name).type("_doc")
                    .source(xContentBuilder);
            PutMappingResponse putMappingResponse = client.admin().indices().putMapping(putMappingRequest).actionGet();
            return putMappingResponse.isAcknowledged();
        }
        return false;
    }



    public Boolean indexDocument(Map<String,Object> map,String entityName){
        IndexResponse indexResponse = client.prepareIndex("h_"+entityName, "_doc")
                .setSource(map, XContentType.JSON)
                .get();
        return indexResponse.getResult().equals(DocWriteResponse.Result.CREATED);
    }

    public List<Map<String,Object>> multiSearch(String kw){
        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        SearchResponse searchResponse = client.prepareSearch()
                .setIndices("h_bbb", "h_ccc")
                .setTypes("_doc")
                .setQuery(queryBuilder)
                .get();
        SearchHits hits = searchResponse.getHits();
        List<Map<String,Object>> list  = new ArrayList<>();
        for (SearchHit hit:hits){
            Map<String, Object> map = hit.getSourceAsMap();
            list.add(map);
        }
        return list;
    }

}
