package com.inspur.springdataelasticsearch.aggregation;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/6
 */
@Data
@Document(indexName = IndexCommonKey.INDEX_NAME,type = IndexCommonKey.TYPE_NAME)
public class TestEntity {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String name;
    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String desc;
    @Field(type = FieldType.Keyword)
    private String brand;
}
