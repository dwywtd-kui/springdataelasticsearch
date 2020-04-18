package com.inspur.springdataelasticsearch.ingestattachment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/4
 */
@Data
@Document(indexName = "test_file_index",type = "_doc")
public class FileDoc {
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Attachment,analyzer ="ik_smart")
    private String data;
}
