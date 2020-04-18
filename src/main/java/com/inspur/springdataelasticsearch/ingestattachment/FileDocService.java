package com.inspur.springdataelasticsearch.ingestattachment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/4
 */
@Service
public class FileDocService {

    @Autowired
    FileDocRepository repository;

    @Autowired
    ElasticsearchTemplate template;

    @Autowired
    TransportClient client;

    private String FIELD_FILE_DATA="attachment.content";

    public void indexFileDoc(FileDoc fileDoc) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = mapper.writeValueAsBytes(fileDoc);
        IndexResponse response = client.prepareIndex("test_file_index", "_doc", fileDoc.getId().toString())
                .setPipeline("single_attachment")
                .setSource(bytes, XContentType.JSON)
                .get();
    }

    public List<FileDoc> query(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        List<FileDoc> fileDocs = template.queryForList(searchQuery, FileDoc.class);
        return fileDocs;
    }

    public List<FileDoc> query2(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        AggregatedPage<FileDoc> fileDocs = template.queryForPage(searchQuery, FileDoc.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<FileDoc> docs = new ArrayList<>();
                SearchHits hits = response.getHits();
                for (SearchHit hit:hits.getHits()){
                    FileDoc doc = new FileDoc();
                    Map<String, Object> source = hit.getSourceAsMap();
                    Long id = Long.parseLong(source.get("id").toString());
                    Map<String,Object> attachment = (Map<String, Object>) source.get("attachment");
                    String content = (String) attachment.get("content");
                    doc.setId(id);
                    doc.setData(content);
                    docs.add(doc);
                }
                return new AggregatedPageImpl(docs);
            }
        });
        return fileDocs.getContent();
    }


    public List<FileDoc> query3(){
        HighlightBuilder.Field fieldTitle = new HighlightBuilder
                .Field(FIELD_FILE_DATA)
                .preTags("<span><font color='#FF0000'>")
                .postTags("</font></span>")
                .numOfFragments(3) //忽略分片
                .fragmentOffset(2)
                .noMatchSize(20);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(FIELD_FILE_DATA,"门票"))
                .withHighlightFields(fieldTitle)
                .build();
        AggregatedPage<FileDoc> fileDocs = template.queryForPage(searchQuery, FileDoc.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<FileDoc> docs = new ArrayList<>();
                SearchHits hits = response.getHits();
                System.out.println(hits.totalHits);
                for (SearchHit hit:hits.getHits()){
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    String data = getFieldString(highlightFields.get(FIELD_FILE_DATA));
                    Map<String, Object> source = hit.getSourceAsMap();
                    Long id = Long.parseLong(source.get("id").toString());
//                    String data = (String) source.get(FIELD_FILE_DATA);
                    FileDoc doc = new FileDoc();
                    doc.setId(id);
                    doc.setData(data);
                    docs.add(doc);
                }
                return new AggregatedPageImpl(docs);
            }
        });
        return fileDocs.getContent();
    }

    private String getFieldString(HighlightField field) {
        StringBuilder sb = new StringBuilder();
        if (field != null) {
            Text[] fragments = field.getFragments();
            for (Text t : fragments) {
                if (!t.equals("")) {
                    sb.append(t.toString());
                }
            }
        }
        return sb.toString();
    }
}
