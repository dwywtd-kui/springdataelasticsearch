package com.inspur.springdataelasticsearch.aggregation;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/6
 */
public class IndexCommonKey {

    public static final String INDEX_NAME = "test_aggs";
    public static final String TYPE_NAME = "_doc";

    public static final String FIELD_BRAND="brand";
    public static final String FIELD_NAME="name";
    public static final String FIELD_DESC="desc";
    public static final String FIELD_ID="id";

    public static final String AGGS_TERMS_BRAND_NAME="group_by_brand";
}
