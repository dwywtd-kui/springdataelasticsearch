package com.inspur.springdataelasticsearch.ingestattachment;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/4
 */
public interface FileDocRepository extends ElasticsearchRepository<FileDoc,Long> {
}
