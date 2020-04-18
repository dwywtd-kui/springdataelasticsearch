package com.inspur.springdataelasticsearch.aggregation;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/6
 */
@Service
@Slf4j
public class TestService {
    private final ElasticsearchTemplate template;
    private final TransportClient client;
    private final TestRepository repository;

    public TestService(ElasticsearchTemplate template, TransportClient client, TestRepository repository) {
        this.template = template;
        this.client = client;
        this.repository = repository;
    }


    /**查询*/
    public void search(String kw,Integer from,Integer size){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery(kw,IndexCommonKey.FIELD_NAME,IndexCommonKey.FIELD_DESC));

        QueryBuilder postFilter = QueryBuilders.termQuery(IndexCommonKey.FIELD_BRAND,"小米");

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms(IndexCommonKey.AGGS_TERMS_BRAND_NAME)
                .field(IndexCommonKey.FIELD_BRAND);

        SearchResponse searchResponse = client.prepareSearch(IndexCommonKey.INDEX_NAME).setTypes(IndexCommonKey.TYPE_NAME)
                .setQuery(queryBuilder)
                .setPostFilter(postFilter)
                .addAggregation(aggregationBuilder)
                .setFrom(from)
                .setSize(size)
                .get();

        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        log.info("查询结果数目："+String.valueOf(totalHits));
    }

    /**查询*/
    public void search2(String kw){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery(kw,IndexCommonKey.FIELD_NAME,IndexCommonKey.FIELD_DESC));
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms(IndexCommonKey.AGGS_TERMS_BRAND_NAME)
                .field(IndexCommonKey.FIELD_BRAND);

        SearchResponse searchResponse = client.prepareSearch(IndexCommonKey.INDEX_NAME).setTypes(IndexCommonKey.TYPE_NAME)
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .get();

        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        log.info("查询结果数目："+String.valueOf(totalHits));
    }


    /**初始化索引mappings*/
    public Boolean initMappings(){
        return template.putMapping(TestEntity.class);
    }

    /** index doc */
    public void indexDoc(TestEntity entity){
        repository.save(entity);
    }
}
