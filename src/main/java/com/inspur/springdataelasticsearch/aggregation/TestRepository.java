package com.inspur.springdataelasticsearch.aggregation;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/6
 */
public interface TestRepository extends ElasticsearchRepository<TestEntity,String> {
}
